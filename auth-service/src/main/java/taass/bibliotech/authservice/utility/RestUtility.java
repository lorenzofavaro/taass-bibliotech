package taass.bibliotech.authservice.utility;

import io.jsonwebtoken.Jwts;

public class RestUtility {

    public static final String HEADER_AUTH = "Authorization";
    public static final String HEADER_TOKEN_PREFIX = "Bearer ";

    public static Long getUserId(String tokenHeader) {
        String token = tokenHeader.replace(HEADER_TOKEN_PREFIX, "");
        return getUserIdFromToken(token);
    }

    private static Long getUserIdFromToken(String token) {
        return (
                (Integer) Jwts.parser()
                        .setSigningKey("IHJg7INqdWUGhCsfZFG/g0X28Q5qxy5DA5Y+CbB2+jI=")
                        .parseClaimsJws(token)
                        .getBody()
                        .get("userId")
        ).longValue();
    }
}
