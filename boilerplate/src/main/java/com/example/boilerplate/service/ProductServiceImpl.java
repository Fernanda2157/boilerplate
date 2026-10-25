package com.example.boilerplate.service;

import com.example.boilerplate.model.Product;
import com.example.boilerplate.dto.ProductDTO; 
import com.example.boilerplate.exception.ResourceNotFoundException; 
import com.example.boilerplate.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                // AQUÍ APLICAMOS EL CAMBIO DE CLEAN CODE
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)); 

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        // Es una buena práctica verificar si existe antes de borrar
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    // Convert Product Entity to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    // Convert ProductDTO to Product Entity
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        return product;
    }
}