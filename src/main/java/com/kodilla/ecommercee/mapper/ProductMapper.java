package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.service.GroupDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMapper {
    private final GroupDbService groupDbService;

    public Product productDtoToProduct(final ProductDto productDto) {
        return new Product(
                productDto.getProductId(),
                productDto.getProductName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getQuantity(),
                groupDbService.getGroup(productDto.getProductGroupId()).orElse(null),
                productDto.getCreateDate());
    }

    public ProductDto productToProductDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getGroup().getId(),
                product.getCreatedDate());
    }

    public List<ProductDto> mapToProductDtoList(final List<Product> productList) {
        return productList.stream()
                .map(this::productToProductDto)
                .toList();
    }

    public List<Product> mapToProductList(final List<ProductDto> productDtoList) {
        return productDtoList.stream()
                .map(this::productDtoToProduct)
                .toList();
    }
}
