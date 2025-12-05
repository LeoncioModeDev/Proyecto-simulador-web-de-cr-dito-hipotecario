package upc.edu.pe.finanzas.dtos;

public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private int expiresIn;
    private UsuariosDTO usuario;

    public LoginResponse() {}

    public LoginResponse(String token, int expiresIn, UsuariosDTO usuario) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }
}