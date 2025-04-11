package com.example.product_catalog.controllers;

import com.example.product_catalog.domain.dtos.ProductRequestDTO;
import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.dtos.ProductUpdateDTO;
import com.example.product_catalog.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;



@RestController
@RequestMapping("/products")
@Tag(name = "Produtos", description = "Operações relacionadas à gestão de produtos")
public class ProductController {

    @Autowired
    private CreateProductService createProductService;
    @Autowired
    private GetAllProductsService getAllProductsService;
    @Autowired
    private GetProductByIdService getProductByIdService;
    @Autowired
    private UpdateProductService updateProductService;
    @Autowired
    private DeleteProductByIdService deleteProductByIdService;

    @Operation(summary = "Criar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(createProductService.execute(productRequestDTO));
    }

    @Operation(summary = "Listar todos os produtos com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAll(@RequestParam(required = false, defaultValue = "") String search, Pageable pageable) {
        Page<ProductResponseDTO> productsPage = getAllProductsService.execute(search,pageable);
        return ResponseEntity.ok(productsPage);
    }

    @Operation(summary = "Buscar um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(getProductByIdService.execute(id));
    }

    @Operation(summary = "Atualizar um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductUpdateDTO productUpdateDTO, @PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(updateProductService.execute(productUpdateDTO, id));
    }

    @Operation(summary = "Excluir um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        deleteProductByIdService.execute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
