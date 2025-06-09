package local.jotape.F.F.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import local.jotape.F.F.domain.model.Produto;
import local.jotape.F.F.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/produtos")
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @GetMapping("/produtos/nome/{nome}")
    public List<Produto> buscarPorNome(@PathVariable String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    @GetMapping("/produtos/cat/{categoria}")
    public List<Produto> buscarPorCategoria(@PathVariable String categoria) {
        return produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
    }

    @GetMapping("/produtos/{produtoID}")
    public ResponseEntity<Produto> buscar(@PathVariable Long produtoID) {

        Optional<Produto> produto = produtoRepository.findById(produtoID);

        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@Valid @RequestBody Produto produto) {

        return produtoRepository.save(produto);
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<Produto> atualizarParcialmente(@Valid @PathVariable Long id, @RequestBody Produto dados) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Produto produto = optionalProduto.get();

        if (dados.getNome() != null) {
            produto.setNome(dados.getNome());
        }
        if (dados.getPreco() != null) {
            produto.setPreco(dados.getPreco());
        }
        if (dados.getCategoria() != null) {
            produto.setCategoria(dados.getCategoria());
        }

        produtoRepository.save(produto);
        return ResponseEntity.ok(produto);
    }
    
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> excluir(@PathVariable(name = "id") Long produtoID) {
        
        //Verifica se o produto existe ou não
        if (!produtoRepository.existsById(produtoID)) {
            return ResponseEntity.notFound().build();
        } 
        
        produtoRepository.deleteById(produtoID);
        return ResponseEntity.noContent().build();
    }
}
