package lanchonete_design_patterns_api.decorator;

import lanchonete_design_patterns_api.enums.TipoAdicional;
import lanchonete_design_patterns_api.model.Produto;

import java.util.List;

public final class AdicionalDecoratorFactory {
    private AdicionalDecoratorFactory() {
    }

    public static Produto aplicar(Produto produto, List<TipoAdicional> adicionais) {
        Produto resultado = produto;
        if (adicionais == null) {
            return resultado;
        }
        for (TipoAdicional adicional : adicionais) {
            resultado = switch (adicional) {
                case BACON -> new ComBacon(resultado);
                case QUEIJO_EXTRA -> new ComQueijoExtra(resultado);
                case MOLHO_ESPECIAL -> new ComMolhoEspecial(resultado);
            };
        }
        return resultado;
    }
}