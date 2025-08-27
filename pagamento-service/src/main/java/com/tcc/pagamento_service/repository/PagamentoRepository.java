package com.tcc.pagamento_service.repository;

import com.tcc.pagamento_service.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
}
