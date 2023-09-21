package com.saadahmedev.cartservice.repository;

import com.saadahmedev.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartByUserId(long userId);

    Optional<Cart> findCartByUserIdAndProductId(long userId, long productId);

    void deleteCartByUserIdAndProductId(long userId, long productId);

    void deleteAllByUserId(long userId);
}
