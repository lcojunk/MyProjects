/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.authentication.JWTokens;

import java.util.Calendar;

/**
 *
 * @author odzhara-ongom leon Class for tokens
 */
public class JWToken {

    public String id;

    public String subject;

    public String issuer;

    public String audience;

    public long createdMillis;

    public long expiredMillis;

    private boolean isValid = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public long getCreatedMillis() {
        return createdMillis;
    }

    public void setCreatedMillis(long createdMillis) {
        this.createdMillis = createdMillis;
    }

    public long getExpiredMillis() {
        return expiredMillis;
    }

    public void setExpiredMillis(long expiredMillis) {
        this.expiredMillis = expiredMillis;
    }

    public boolean isExpired() {
        Calendar now = Calendar.getInstance();

        Calendar expiration = Calendar.getInstance();
        expiration.setTimeInMillis(expiredMillis);

        return now.after(expiration);
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid && !isExpired();
    }
}
