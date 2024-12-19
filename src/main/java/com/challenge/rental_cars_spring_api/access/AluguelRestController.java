package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.queries.ImportarRelatorioAluguelQuery;
import com.challenge.rental_cars_spring_api.core.queries.ListarAlugueisQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/aluguel")
@RequiredArgsConstructor
public class AluguelRestController {

    private final ImportarRelatorioAluguelQuery importarRelatorioAluguelQuery;
    private final ListarAlugueisQuery listarAlugueisQuery;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Arquivo inválido ou malformado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<?> importarRelatorioAluguel(@RequestParam("file") MultipartFile file) throws IOException {
        importarRelatorioAluguelQuery.execute(file);
        return new ResponseEntity<>(ResponseEntity.ok(), HttpStatus.OK);
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista com os alugueis encontrados.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListarAlugueisQueryResultItem.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<List<ListarAlugueisQueryResultItem>> listarAlugueis() {
        return new ResponseEntity<>(listarAlugueisQuery.execute(), HttpStatus.OK);
    }

}
