package caio.caminha.ControleVeiculo.exceptions;

public class UsuarioInvalidoException extends Exception{
    private final String message;

    public UsuarioInvalidoException(String message){
        super(message);
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    @Override
    public String toString() {
        return "UsuarioInvalidoException{" +
                "message='" + message + '\'' +
                '}';
    }
}
