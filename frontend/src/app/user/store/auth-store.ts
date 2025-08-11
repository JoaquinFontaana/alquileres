import { computed, inject } from "@angular/core";
import { AuthenticatedUser, JwtPayload, LoginRequest, LoginResponse } from "@models";
import { signalStore, withMethods, withState, patchState, withHooks, withComputed} from "@ngrx/signals";
import {jwtDecode } from "jwt-decode"
import { AuthService } from "../service/auth";
import { rxMethod} from "@ngrx/signals/rxjs-interop";
import { pipe, switchMap, tap } from 'rxjs';
import { tapResponse } from "@ngrx/operators";
import { jwtPayloadAdapter } from "../adapters/jwt-payload-adapter";
import { TOKEN_KEY } from "@shared/consts";
import { HttpErrorResponse } from "@angular/common/http";

interface AuthState{
    user: AuthenticatedUser | null,
    token: string | null,
    isLoading: boolean,
    error: string | null
}

const initialState: AuthState = {
  user: null,
  token: null, 
  isLoading: false,
  error: null,
};

function decodeJwt(token:string):JwtPayload{
    return jwtDecode(token)
}

function extractUserFromToken(token:string):AuthenticatedUser{
    return jwtPayloadAdapter(decodeJwt(token))
}

function isTokenValid(token: string | null): boolean {
  if (!token) return false;
    const payload = decodeJwt(token);
    return payload.exp * 1000 > Date.now();
}

function getStoredToken(): string | null {
  if (typeof window === 'undefined') return null;
  return localStorage.getItem(TOKEN_KEY);
}

export const AuthStore = signalStore(
    {providedIn: 'root'},
    withState(initialState),
    
    withComputed(({user}) =>({
        userRole: computed(() => user()?.rol || ''),
        userMail: computed(() => user()?.mail || ''),
    })),

    withMethods((store,authService = inject(AuthService)) =>({

        login: rxMethod<LoginRequest>(
            pipe(
                tap(() => patchState(store,{error:null,isLoading:true})),
                switchMap((credentials) => 
                    authService.login(credentials).pipe(
                        tapResponse({
                            next: (response:LoginResponse) => { 
                                const {token} = response
                                const user = extractUserFromToken(token)
                                localStorage.setItem(TOKEN_KEY, token);
                                patchState(store,{token, user});
                            },
                            error: (error: HttpErrorResponse) => {
                                patchState(store, { error: `Error al realizar el login: ${error.message}` });
                            },
                            finalize: () => {
                                patchState(store,{isLoading:false})
                            }
                        })
                    )
                )
            )
        ),
        logout():void{
            this.clearAuthState()
        },
        hasRole(role:string):boolean {
            return store.userRole() === (role)
        },
        clearAuthState(): void {
            localStorage.removeItem(TOKEN_KEY);
            patchState(store, { token: null, user: null });
        },
        isTokenValid(): boolean {
            const token = store.token();
            if (!token || !isTokenValid(token)) {
                this.clearAuthState();
                return false;
            }
            return true;
        }
    })),
    withHooks({
        onInit(store) {
            const token = getStoredToken();
            if (token && isTokenValid(token)) {
                const user = extractUserFromToken(token);
                patchState(store,{ token, user });
            } else {
                store.clearAuthState()
            }
        }
  }),
)