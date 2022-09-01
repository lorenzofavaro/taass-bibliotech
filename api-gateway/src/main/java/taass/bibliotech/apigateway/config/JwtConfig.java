package taass.bibliotech.apigateway.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{1000*60*60*10}}")
    private int expiration;

    @Value("${security.jwt.secret:IHJg7INqdWUGhCsfZFG/g0X28Q5qxy5DA5Y+CbB2+jI=}")
    private String secret;

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getSecret() {
        return secret;
    }

}
