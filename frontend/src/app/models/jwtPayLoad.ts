export interface JwtPayload{
    sub:string
    roles:string
    sucursal:string
    iat:number
    exp:number
}