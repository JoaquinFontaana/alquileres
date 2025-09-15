import { AuthenticatedUser, JwtPayload } from "@models";

export function jwtPayloadAdapter(payload:JwtPayload):AuthenticatedUser{
    return {
        email: payload.sub,
        rol: payload.roles
    }
}