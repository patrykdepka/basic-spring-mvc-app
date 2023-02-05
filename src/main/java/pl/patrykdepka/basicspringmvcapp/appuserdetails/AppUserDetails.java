package pl.patrykdepka.basicspringmvcapp.appuserdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUserDetails extends User {
    private String firstName;
    private String lastName;

    public AppUserDetails(AppUserDetailsBuilder appUserDetailsBuilder) {
        super(
                appUserDetailsBuilder.username,
                appUserDetailsBuilder.password,
                appUserDetailsBuilder.enabled,
                true,
                true,
                appUserDetailsBuilder.accountNonLocked,
                appUserDetailsBuilder.authorities
        );
        this.firstName = appUserDetailsBuilder.firstName;
        this.lastName = appUserDetailsBuilder.lastName;
    }

    public static final class AppUserDetailsBuilder {
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private boolean enabled;
        private boolean accountNonLocked;
        private List<GrantedAuthority> authorities;

        public AppUserDetailsBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AppUserDetailsBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AppUserDetailsBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public AppUserDetailsBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public AppUserDetailsBuilder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public AppUserDetailsBuilder withAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public AppUserDetailsBuilder withRoles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList(roles.length);
            String[] var3 = roles;
            int var4 = roles.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String role = var3[var5];
                Assert.isTrue(!role.startsWith("ROLE_"), () -> {
                    return role + " cannot start with ROLE_ (it is automatically added)";
                });
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            return this.authorities((Collection)authorities);
        }

        public AppUserDetailsBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList(authorities);
            return this;
        }

        public AppUserDetails build() {
            return new AppUserDetails(this);
        }
    }
}
