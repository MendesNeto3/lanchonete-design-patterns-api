package lanchonete_design_patterns_api.chain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidadorPedidoChainConfig {

    @Bean
    public ValidadorPedido cadeiaValidacao(ValidadorClienteBloqueado validadorCliente,
                                           ValidadorEstoque validadorEstoque,
                                           ValidadorValorMinimo validadorValor) {
        validadorCliente.setProximo(validadorEstoque).setProximo(validadorValor);
        return validadorCliente;
    }
}
