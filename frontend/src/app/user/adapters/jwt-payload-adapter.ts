import { AuthenticatedUser, JwtPayload } from "@models";

export function jwtPayloadAdapter(payload:JwtPayload):AuthenticatedUser{
    return {
        mail: payload.sub,
        rol: payload.roles
    }
}