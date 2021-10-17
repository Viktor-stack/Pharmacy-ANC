package com.pharmacyANC.PharmacyANC.busines.service;


import com.pharmacyANC.PharmacyANC.busines.model.User;
import com.pharmacyANC.PharmacyANC.connectionDb.ConectionDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { // Impl в названии класса означает "Implementation" - реализация
    private final ConectionDBService conectionDBService;

    @Autowired
    public UserDetailsServiceImpl(ConectionDBService conectionDBService) {
        this.conectionDBService = conectionDBService;
    }

    @Override
    @Transactional
    // метод ищет пользователя по username или email (любое совпадение)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // этот метод используется при аутентификации пользователя

        // используем обертку Optional - контейнер, который хранит значение или null - позволяет избежать ошибки NullPointerException
        Optional<User> userOptional = conectionDBService.findByUserName(username);

        if (userOptional.isEmpty()) { // если не нашли по имени
            userOptional = conectionDBService.findByEmail(username); // пытаемся найти по email
        }

        if (userOptional.isEmpty()) { // если не нашли ни по имени, ни по email
            throw new UsernameNotFoundException("User Not Found with username or email: " + username); // выбрасываем исключение, которое можно отправить клиенту
        }

        return new UserDetailsImpl(userOptional.get()); // если пользователь в БД найден - создаем объект UserDetailsImpl (с объектом User внутри), который потом будет добавлен в Spring контейнер и в объект Principal
    }
}
