package com.tuapp.service;

import com.tuapp.config.RabbitMQConfig;
import com.tuapp.dto.PedidoDTO;
import com.tuapp.entity.Pedido;
import com.tuapp.entity.User;
import com.tuapp.mapper.PedidoMapper;
import com.tuapp.repository.PedidoRepository;
import com.tuapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UserRepository userRepository;
    private final PedidoMapper pedidoMapper;
    private final RabbitTemplate rabbitTemplate;

    public List<PedidoDTO> findAllByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return pedidoRepository.findByUserId(user.getId()).stream()
                .map(pedidoMapper::toDto)
                .collect(Collectors.toList());
    }

    public PedidoDTO registrarPedido(PedidoDTO pedidoDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido.setUser(user);
        pedido.setEstado("PENDIENTE");
        if (pedido.getFechaCreacion() == null) {
            pedido.setFechaCreacion(LocalDateTime.now());
        }
        Pedido saved = pedidoRepository.save(pedido);
        PedidoDTO result = pedidoMapper.toDto(saved);

        // Enviar a RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_PEDIDOS, RabbitMQConfig.ROUTING_KEY_PEDIDOS, result);

        return result;
    }

    public void actualizarEstado(Long id, String estado) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        pedido.setEstado(estado);
        pedidoRepository.save(pedido);
    }
}
