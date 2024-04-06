package com.beautyzone.beautysalonapp.domain;

import com.beautyzone.beautysalonapp.constants.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User implements UserDetails {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private String quote;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Appointment> clientAppointments;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "employee_service",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "service_id") }
    )
    private List<Service> services;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_shift",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "shift_id") }
    )
    private List<Shift> shifts;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> employeeAppointments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword(){
        return password;
    }

}
