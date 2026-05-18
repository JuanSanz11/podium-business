package com.tuapp.service;

import com.tuapp.config.RabbitMQConfig;
import com.tuapp.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoListener {

    private final PedidoService pedidoService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDOS)
    public void processPedido(PedidoDTO pedidoDTO) {
        log.info("Procesando pedido de la cola: {}", pedidoDTO);
        try {
            // Simulamos procesamiento
            Thread.sleep(2000);
            pedidoService.actualizarEstado(pedidoDTO.getId(), "PROCESADO");
            log.info("Pedido {} procesado con éxito", pedidoDTO.getId());
        } catch (InterruptedException e) {
            log.error("Error al procesar el pedido", e);
            Thread.currentThread().interrupt();
        }
    }
}
