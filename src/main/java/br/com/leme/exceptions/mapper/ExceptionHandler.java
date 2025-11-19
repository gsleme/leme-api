package br.com.leme.exceptions.mapper;
import br.com.leme.dto.ErrorResponseDTO;
import br.com.leme.exceptions.*;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable e) {
        if (e instanceof DatabaseException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponseDTO(
                e.getMessage() + "Causa: " + e.getCause().getMessage())).build();
        }

        if (e instanceof EntityNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponseDTO(e.getMessage())).build();
        }

        if (e instanceof IncorrectPasswordException) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponseDTO(e.getMessage())).build();
        }

        if (e instanceof InvalidIdFormatException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponseDTO(e.getMessage())).build();
        }

        if (e instanceof NotAllowedException) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(new ErrorResponseDTO(
                "Requisição indisponível para este endpoint.")
            ).build();
        }

        if (e instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponseDTO(
                "Endpoint não encontrado.")
            ).build();
        }

        return Response.serverError().entity(new ErrorResponseDTO("Erro inesperado, verifique disponibilidade do servidor. " + e)).build();
    }
}
