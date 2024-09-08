package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.ItemDto;
import com.kodilla.ecommercee.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public List<ItemDto> mapProductListToItemDtoList(List<Product> productList) {
        return productList.stream()
                .collect(Collectors.groupingBy(Product::getId, Collectors.counting()))
                .entrySet().stream()
                .map(e -> new ItemDto(e.getKey(), e.getValue()))
                .toList();
    }
}
