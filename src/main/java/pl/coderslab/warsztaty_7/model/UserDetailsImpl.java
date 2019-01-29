//package pl.coderslab.warsztaty_7.model;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//@Component
//public class UserDetailsImpl extends User implements UserDetails {
//
//    public UserDetailsImpl(User user) {
//        super(user);
//    }
//
//    public UserDetailsImpl(){ }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public String getPassword() {
//        return super.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return super.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return super.isEnabled();
//    }
//
//    public User getUser() {
//        return this;
//    }
//}
