package com.arraypay.market.dao;

import com.arraypay.market.dto.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
