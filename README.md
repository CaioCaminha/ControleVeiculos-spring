
# API Rest com Spring + Hibernate

Nesse post irei compartilhar o desenvolvimento de uma API Rest, utilizando a linguagem Java e os frameworks Spring e Hibernate.

  

De antemão disponibilizo os links a seguir:

-   [Repositório no GitHub.](https://github.com/CaioCaminha/ControleVeiculos-OrangeTalents)
    
-   [API em funcionamento, hospedada no Heroku.](https://controle-veiculos-springboot.herokuapp.com/swagger-ui.html)
    
-   [Aplicação Front-End consumindo esta API.](https://controle-veiculos-front.herokuapp.com/)
    

## Contexto

O serviço a ser implementado consiste em uma API para controle e gerenciamento de veículos, contando com informações como o valor do veículo segundo a tabela FIPE. Dentre as funcionalidades da aplicação se destacam as seguintes:

- Criar um Usuário.

- Cadastrar um veículo.

- Listar os veículos referente a um usuário específico.

## Desenvolvimento

A aplicação será desenvolvida utilizando Spring Boot(criação e gerenciamento do projeto), Spring Data Jpa (comunicação com o Banco de dados), Spring Security (autenticação e autorização), Spring-Cloud-Feign (consumo de uma API externa), e o banco de dados utilizado será o PostgreSQL.

### Entidades e Repositórios

A primeira entidade trata-se de **Usuario**, com os campos: API Rest com Spring + Hibernate

Nesse post irei compartilhar o desenvolvimento de uma API Rest, utilizando a linguagem Java e os frameworks Spring e Hibernate.

  

De antemão disponibilizo os links a seguir:

-   [Repositório no GitHub.](https://github.com/CaioCaminha/ControleVeiculos-OrangeTalents)
    
-   [API em funcionamento, hospedada no Heroku.](https://controle-veiculos-springboot.herokuapp.com/swagger-ui.html)
    
-   [Aplicação Front-End consumindo esta API.](https://controle-veiculos-front.herokuapp.com/)
    

## Contexto

O serviço a ser implementado consiste em uma API para controle e gerenciamento de veículos, contando com informações como o valor do veículo segundo a tabela FIPE. Dentre as funcionalidades da aplicação se destacam as seguintes:

- Criar um Usuário.

- Cadastrar um veículo.

- Listar os veículos referente a um usuário específico.

## Desenvolvimento

A aplicação será desenvolvida utilizando Spring Boot(criação e gerenciamento do projeto), Spring Data Jpa (comunicação com o Banco de dados), Spring Security (autenticação e autorização), Spring-Cloud-Feign (consumo de uma API externa), e o banco de dados utilizado será o PostgreSQL.

-   ### Entidades e Repositórios
    
A primeira entidade trata-se de **Usuario**, com os campos: **nome**, **email**, **cpf**, **password** e **nascimento**, além do **ID** gerado automaticamente.Sendo os campos de email e cpf únicos, para evitar usuários duplos e conflito durante a autenticação.

A segunda trata-se de **Veiculo**, possuindo os atributos: **tipo**(carro, moto ou caminhão), **marca**, **modelo**, **ano**, **valor**, **diaRodizio**, **combustivel** (Gasolina ou Diesel) e um relacionamento **ManyToOne** com a entidade **Usuario**. Os atributos **tipo** e **combustivel** serão explicados no decorrer do post.

Tendo as entidades devidamente implementadas, partimos para a criação dos repositórios da nossa aplicação(interfaces que farão a comunicação com a base de dados).

        public interface UsuarioRepository extends JpaRepository<Usuario, Long>{  
		    Optional<Usuario> findByEmail(String email);  
		    Optional<Usuario> findByCpf(String email);  
	    }
Agora entenderemos a estrutura da aplicação com o diagrama a abaixo:
**![](https://lh3.googleusercontent.com/u-pt7UbdZWgxADWEVw-MaDz6LXidtOFmUn7unr-HlqyuFFubHuoOHdTWu3UzWZ7uTBLSZErMpN2sm3_ID6C2zumQKcK_27qHnv40vw_YylAFbLPFr3z3aP9-AltZte2ZmSgV1BzK)**
Pode-se observar que as entidades estão completamente isoladas do resto da aplicação, tendo contato unicamente com os repositórios, tornando-as blindadas de qualquer alteração no restante do projeto, além de proteger informações pessoais como a senha. Sendo assim, os **controllers** e **serviços** não terão contato com as entidades, mas sim com um modelo de **input** e **output**, tanto para **Usuario** quanto para **Veiculo**.

  
As classes de **Input** terão a mesma estrutura para as duas entidades, consistem na declaração dos atributos a serem recebidos na requisição e na implementação de um método **convert** que retorna uma instância de sua respectiva entidade

    public class InputUsuario {  
      @NotNull @NotEmpty  
      @JsonProperty("nome")  
      private String nome;  
      @NotNull @NotEmpty  
      @JsonProperty("email")  
      private String email;  
      @NotNull @NotEmpty  
      @JsonProperty("cpf")  
      private String cpf;  
      @NotNull @NotEmpty  
      @JsonProperty("password")  
      private String password;  
      @NotNull @NotEmpty  
      @JsonProperty("nascimento")  
      private String nascimento;  
      
       public InputUsuario(){}  
      
      public InputUsuario(String nome,String email, String cpf, String password, String nascimento){  
            this.nome = nome;  
            this.email = email;  
            this.cpf = cpf;  
            this.password = password;  
            this.nascimento = nascimento;  
        }  
      
      public Usuario convert() throws ParseException {  
      return new Usuario(this.nome,  
                                this.email,  
                                this.cpf,  
                                new BCryptPasswordEncoder().encode(this.password),  
                                this.nascimento);  
        }
As classes de Output consistem em um modelo de resposta que serão enviadas juntamente ao ResponseEntity, como retorno da requisição, e ambas possuem quase a mesma estrutura, sendo a declaração dos atributos a serem retornados e outro método convert que auxiliará na paginação da resposta. Com única diferença de que o output referente a Veiculo terá as propriedades diaRodizio e rodizioAtivo, e terão seus valores implementados posteriormente no VeiculoService, da mesma forma que o atributo valor.

	

    public class OutputVeiculo {  
    	  private Long id;  
    	    private String tipo;  
    	    private String marca;  
    	    private String modelo;  
    	    private Integer ano;  
    	    private String valor;  
    	    private String diaRodizio;  
    	    private String combustivel;  
    	    private String usuario;  
    	    private boolean rodizioAtivo;  
    	  
    	    public OutputVeiculo(){}  
    	  
    	  public OutputVeiculo(Veiculo veiculo){  
    	  this.id = veiculo.getId();  
    	        this.tipo = veiculo.getTipo();  
    	        this.marca = veiculo.getMarca();  
    	        this.modelo = veiculo.getModelo();  
    	        this.ano = veiculo.getAno();  
    	        this.valor = veiculo.getValor();  
    	        this.combustivel = veiculo.getCombustivel();  
    	        this.usuario = veiculo.getUsuario().getEmail();  
    	        this.diaRodizio = veiculo.getDiaRodizio();  
    	    }
  ### Consumindo API externa com Feign
    

Partiremos agora, para a implementação das classes referentes ao consumo da API da FIPE, utilizando o **Spring-Cloud-Feign**.

  
A interface principal será a **FipeClient** que irá dispor os métodos de consulta para a API externa, sendo eles: **getMarcas**, **getModelos** e um **getVeiculoFipe** para cada tipo de veículo. 

Nessa interface veremos a importância dos atributos **tipo** e **combustivel** da entidade Veículo, que possibilita reduzir o número de requisições para a API externa e torna o código mais legível.

    @FeignClient(url = "https://parallelum.com.br/fipe/api/v1/", name = "fipe")  
    public interface FipeClient {  
      
      @GetMapping("{tipoVeiculo}/marcas/{codigoMarca}/modelos")  
      Modelo getModelos(@PathVariable("codigoMarca") int codigoMarca,  
                          @PathVariable("tipoVeiculo") String tipoVeiculo);  
      
        @GetMapping("{tipoVeiculo}/marcas")  
      ArrayList<ObjectFipe> getMarcas(@PathVariable("tipoVeiculo")String tipoVeiculo);

Os Métodos acima têm como função requisitar para a API as marcas e os modelos referentes ao tipo de veículo especificado pelo usuário, tendo como retorno um **Modelo** e **ObjectFipe**, classes que representam o modelo de resposta da API.

    @GetMapping("carros/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{ano}-1")  
      VeiculoFipe getVeiculoFipeCarroGasolina(@PathVariable("codigoMarca") int codigoMarca,  
                                                @PathVariable("codigoModelo") int codigoModelo,  
                                                @PathVariable("ano") int ano);  
      
        @GetMapping("carros/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{ano}-3")  
      VeiculoFipe getVeiculoFipeCarroDiesel(@PathVariable("codigoMarca") int codigoMarca,  
                                              @PathVariable("codigoModelo") int codigoModelo,  
                                              @PathVariable("ano") int ano);  
      
        @GetMapping("motos/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{ano}-1")  
      VeiculoFipe getVeiculoFipeMoto(@PathVariable("codigoMarca") int codigoMarca,  
                                       @PathVariable("codigoModelo") int codigoModelo,  
                                       @PathVariable("ano") int ano);  
      
        @GetMapping("caminhoes/marcas/{codigoMarca}/modelos/{codigoModelo}/anos/{ano}-3")  
      VeiculoFipe getVeiculoFipeCaminhoes(@PathVariable("codigoMarca") int codigoMarca,  
                                            @PathVariable("codigoModelo") int codigoModelo,  
                                            @PathVariable("ano") int ano);  
    }
Os métodos restantes têm como função receber informações completas na requisição para a API, com o intuito de obter a informação referente ao atributo **valor** da entidade **Veiculo**.

## Serviços

### Serviço de Usuário
    

Chegamos ao momento de desenvolvermos os serviços da aplicação, iniciaremos pelo **UsuarioService**. A classe possui um único método, responsável por validar e salvar um usuário na base de dados.

    @Service  
    public class UsuarioService {  
      
      @Autowired  
      private UsuarioRepository usuarioRepository;  
      
        public OutputUsuario save(InputUsuario input) throws Exception {  
      Usuario usuario = input.convert();  
            if(!this.usuarioRepository.findByEmail(usuario.getEmail()).isPresent() &&  
                    !this.usuarioRepository.findByCpf(usuario.getCpf()).isPresent() ){  
      
      this.usuarioRepository.save(usuario);  
                return new OutputUsuario(usuario);  
            }  
      throw new UsuarioInvalidoException("Email ou CPF inválidos, tente novamente!");  
        }  
      
    }

O método verifica se os campos Email e CPF não estão contidos na base de dados e caso essa condição seja `true`, utiliza o **UsuarioRepository** para cadastrar o registro no banco de dados. Caso a condição passada retorne false, será lançada a exception **UsuarioInvalidoException**.

### Serviço de Veículo
    

Para melhor legibilidade do código nos serviços referentes aos veículos, utilizaremos o `polimorfismo` para reaproveitar código, implementando a classe **VeiculoClient**, que proverá métodos de consultas.

    public class VeiculoClient {  
      private FipeClient fipeClient;  
      
        public VeiculoClient(FipeClient fipeClient){  
      this.fipeClient = fipeClient;  
        }  
      
      public int getCodigoMarca(String nomeMarca, String tipo){  
      ArrayList<ObjectFipe> marcas =  this.fipeClient.getMarcas(tipo);  
            for(ObjectFipe marca:marcas){  
      if(marca.getNome().equals(nomeMarca)){  
      return Integer.parseInt(marca.getCodigo());  
                }  
     }  return 0;  
        }  
      public int getCodigoModelo(String nomeModelo, int codigoMarca, String tipo){  
      Modelo modelos =  this.fipeClient.getModelos(codigoMarca, tipo);  
      
            List<ObjectFipe> modelo = modelos.getModelos();  
      
            for(ObjectFipe modeloObject : modelo){  
      if(modeloObject.getNome().equals(nomeModelo)){  
      return Integer.parseInt(modeloObject.getCodigo());  
                }  
     }  return 0;  
        }  
      
      
    }
 Essa classe tem como finalidade consumir os métodos da interface **FipeClient**, montando a requisição e iterando com um `ForEach` sobre a resposta, verificando a igualdade entre os dados retornados e os dados do usuário por meio do método `equals`.

  

O primeiro método do serviço de **Veiculo**(**VeiculoService**) é responsável por resgatar os registros referentes a um **Usuario** em específico, por meio do **VeiculoRepository**, permitindo que seja retornado apenas os registros referentes ao usuário que estará logado (Você entenderá essa parte mais adiante).

     public Page<OutputVeiculo> getVeiculos(String token, Pageable pageable){  
      Long id = this.tokenService.getUserId(token.substring(7, token.length()));  
      
        String userName = this.usuarioRepository.getById(id).getEmail();  
      
        Page<Veiculo> veiculos = this.veiculoRepository.findByUsuarioEmail(userName, pageable);  
      
        return OutputVeiculo.convert(veiculos);  
    }
Também teremos o método **saveVeiculo**, onde o fluxo de dados será direcionado para o método referente ao tipo de veículo inserido.

    public OutputVeiculo saveVeiculo(InputVeiculo input, Usuario usuario) throws VeiculoInvalidoException {  
      if (TipoVeiculo.MOTO.getTipo().equals(input.getTipo())) {  
      return saveMoto(input, usuario);  
        }else if(TipoVeiculo.CARRO.getTipo().equals(input.getTipo())){  
      return saveCarro(input, usuario);  
        }  
      return saveCaminhao(input, usuario);  
    }
Os métodos se dividem em saveMoto, saveCarro e saveCaminhao, que chamará o seu respectivo serviço.

    public OutputVeiculo saveCarro(InputVeiculo input, Usuario usuario) throws VeiculoInvalidoException{  
      if(this.validaVeiculo(input)) {  
      Veiculo veiculo = input.convert();  
      
            this.carroService.montaCarro(veiculo, input, usuario);  
            this.veiculoRepository.save(veiculo);  
      
            Calendar cal = Calendar.getInstance();  
            OutputVeiculo output = new OutputVeiculo(veiculo);  
      
            output.setRodizioAtivo(output.getDiaRodizio().equals(this.weekDay(cal)));  
            return output;  
        }  
      throw new VeiculoInvalidoException("Não foi possível salvar o Carro no Banco de Dados");  
      
    }
Caso haja um erro durante esse processo, é lançada a `exception` **VeiculoInvalidoException**, e retornado para o usuário um código 400.

Cada serviço referente ao tipo estende a classe **VeiculoClient**, sendo responsável pela sobrescrita de seus métodos, os serviços referentes aos tipos possuem estrutura extremamente semelhantes.

    @Service  
    public class MotoService extends VeiculoClient{  
      @Autowired  
      private FipeClient fipeClient;  
        public MotoService(FipeClient fipeClient) {  
      super(fipeClient);  
        }  
      
      public void montaMoto(Veiculo veiculo, InputVeiculo input, Usuario usuario){  
      int codigoMarcaMoto = this.getCodigoMarcaMoto(input.getMarca());  
            int codigoModeloMoto = this.getCodigoModeloMoto(input.getModelo(), codigoMarcaMoto);  
            int ano = Integer.parseInt(input.getAno());  
      
            VeiculoFipe veiculoFipe = this.getVeiculoFipeMoto(codigoMarcaMoto, codigoModeloMoto, ano);  
            veiculo.setUsuario(usuario);  
            veiculo.setValor(veiculoFipe.getValor());  
            veiculo.setDiaRodizio(this.diaDoRodizio(input.getAno()));  
        }  
      
      private String diaDoRodizio(String ano){  
      if(ano.substring(3).equals("0") || ano.substring(3).equals("1")){  
      return DiaSemana.SEGUNDA.getDiaDaSemana();  
            }  
      if(ano.substring(3).equals("2") || ano.substring(3).equals("3")){  
      return DiaSemana.TERCA.getDiaDaSemana();  
            }  
      if(ano.substring(3).equals("4") || ano.substring(3).equals("5")){  
      return DiaSemana.QUARTA.getDiaDaSemana();  
            }  
      if(ano.substring(3).equals("6") || ano.substring(3).equals("7")){  
      return DiaSemana.QUINTA.getDiaDaSemana();  
            }  
      if (ano.substring(3).equals("8") || ano.substring(3).equals("9")){  
      return DiaSemana.SEXTA.getDiaDaSemana();  
            }else{  
      return "segunda-feira";  
            }  
     }  
      private int getCodigoMarcaMoto(String nomeMarca){  
      return super.getCodigoMarca(nomeMarca, TipoVeiculo.MOTOS.getTipo());  
        }  
      
      private int getCodigoModeloMoto(String nomeModelo, int codigoMarca){  
      return super.getCodigoModelo(nomeModelo, codigoMarca, TipoVeiculo.MOTOS.getTipo());  
        }  
      
      private VeiculoFipe getVeiculoFipeMoto(int codigoMarca, int codigoModelo, int ano){  
      return this.fipeClient.getVeiculoFipeMoto(codigoMarca, codigoModelo, ano);  
        }  
    }
No serviço do tipo carro, podemos observar claramente a função do atributo **combustivel**, sendo essa, a única diferença entre os serviços de veículo.

    private void verificaCombustivel(Veiculo veiculo,  
                                     InputVeiculo input,  
                                     int codigoMarcaCarro,  
                                     int codigoModeloCarro,  
                                     int ano){  
      VeiculoFipe veiculoFipe;  
        if(input.getCombustivel().equals("Gasolina")){  
      veiculoFipe = this.getVeiculoFipeCarroGasolina(codigoMarcaCarro, codigoModeloCarro, ano);  
        }else {  
      veiculoFipe = this.getVeiculoFipeCarroDiesel(codigoMarcaCarro, codigoModeloCarro, ano);  
        }  
      veiculo.setValor(veiculoFipe.getValor());  
        veiculo.setDiaRodizio(this.diaDoRodizio(input.getAno()));  
    }
O método acima, direciona o fluxo de dados de acordo com o atributo **combustivel** enviado no **input**, após o retorno desta chamada os atributos valor e **diaRodizio** (através do método **diaDoRodizio**) têm seus valores implementados.

    private String diaDoRodizio(String ano){  
      if(ano.substring(3).equals("0") || ano.substring(3).equals("1")){  
      return DiaSemana.SEGUNDA.getDiaDaSemana();  
        }  
      if(ano.substring(3).equals("2") || ano.substring(3).equals("3")){  
      return DiaSemana.TERCA.getDiaDaSemana();  
        }  
      if(ano.substring(3).equals("4") || ano.substring(3).equals("5")){  
      return DiaSemana.QUARTA.getDiaDaSemana();  
        }  
      if(ano.substring(3).equals("6") || ano.substring(3).equals("7")){  
      return DiaSemana.QUINTA.getDiaDaSemana();  
        }  
      if (ano.substring(3).equals("8") || ano.substring(3).equals("9")){  
      return DiaSemana.SEXTA.getDiaDaSemana();  
        }else{  
      return "segunda-feira";  
        }  
    }
## Autenticação e Autorização

Trataremos de uma funcionalidade que julgo essencial para esta API: a **autenticação** e **autorização** dos usuários no sistema. Para listar os veículos de acordo com o usuário que os cadastrou, iremos utilizar o sistema de tokens **JWT** (enviados em um `header` **Authorization**) juntamente ao **Spring Security**, isolando o acesso do usuário somente a registros cadastrados por ele próprio.

### Implementando o Spring Security.
    

Iniciaremos com a implementação da classe **SecurityConfigurations** que estenderá outra classe chamada **WebSecurityConfigurerAdapter** e irá sobrescrever o método **configure** que recebe uma instância de `HttpSecurity`, referente as configurações de autorização de acesso.

    protected void configure(HttpSecurity http) throws Exception {  
      http.authorizeRequests()  
      .antMatchers("/usuarios").permitAll()  
      .antMatchers( "/auth").permitAll()  
      .anyRequest().authenticated()  
      .and().cors()  
      .and().csrf().disable()  
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  
      .and().addFilterBefore(new AuthenticationTokenFilter(this.usuarioRepository, this.tokenService),  
                                                                        UsernamePasswordAuthenticationFilter.class);  
    }
Como pode ser observado no trecho de código acima, liberamos o acesso aos endpoints **“/usuarios”** e **“/auth”**, permitindo o cadastro e autenticação do usuário. Além disso, determinamos que a política de criação de sessão será **STATELESS** e adicionamos um filtro que será melhor explicado nos próximos passos.

### Serviços de Autenticação
    

Daremos início a criação do **TokenService**, o serviço terá três métodos, o **generateToken**, **isTokenValid**, **getUserId**.

    public String generateToken(Authentication authentication){  
      Usuario usuarioLogado = (Usuario) authentication.getPrincipal();  
        Date today = new Date();  
        Date expirationDate = new Date(today.getTime() + Long.parseLong(this.expiration));  
      
        return Jwts.builder()  
      .setIssuer("API Orange Talents")  
      .setSubject(usuarioLogado.getId().toString())  
      .setIssuedAt(today)  
      .setExpiration(expirationDate)  
      .signWith(SignatureAlgorithm.HS256, secret)  
      .compact();  
    }
O método **generateToken** terá a função de retornar uma instância de `Jwts`, definindo como **subject**, o **ID** do usuário e o **SignatureAlgorithm** sendo **HS256**. O serviço proverá também mais dois outros métodos.

    public boolean isTokenValid(String token){  
      try{  
      Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);  
            return true;  
        }catch (Exception e){  
      return false;  
        }  
    }  
      
    public Long getUserId(String token){  
      Claims claims = Jwts.parser()  
      .setSigningKey(this.secret)  
      .parseClaimsJws(token)  
      .getBody();  
        return Long.parseLong(claims.getSubject());  
    }
O **isTokenValid** é responsável por verificar se o token recebido é capaz de gerar uma instância de `Claims`, e o **getUserId** que recebe um token, realiza o parse para um objeto `Claims`, podendo assim capturar o seu **subject**(No caso o **Id** do usuário).

  
Criaremos também o **AuthenticationService** - classe que implementa o **UserDetailsService** e sobrescreve o método **loadUserByUsername** responsável por capturar o usuário ativo por meio do seu **username**.

    @Service  
    public class AuthenticationService implements UserDetailsService {  
      
      @Autowired  
      private UsuarioRepository repository;  
      
        @Override  
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
      Optional<Usuario> usuario = repository.findByCpf(username);  
      
            if(usuario.isPresent()){  
      return usuario.get();  
            }  
      throw new UsernameNotFoundException("username inválido");  
        }  
    }
O próximo passo é criar o filtro citado anteriormente para interceptar a requisição e realizar as validações do token trazido. Esta classe deve estender a classe **OncePerRequestFilter** e tem de sobrescrever o método **doFilterInternal**.

    @Override  
    protected void doFilterInternal(@NotNull HttpServletRequest request,  
                                    @NotNull HttpServletResponse response,  
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {  
      String token = recuperarToken(request);  
        boolean valid = this.tokenService.isTokenValid(token);  
        if(valid){  
      autenticarCliente(token);  
        }  
      filterChain.doFilter(request, response);  
    }
O método **doFilterInternal** realiza uma chamada para **recuperarToken**, passando um objeto do tipo `x`.

    public String recuperarToken(HttpServletRequest request){  
      String token = request.getHeader("Authorization");  
        if(token==null || token.isEmpty() || !token.startsWith("Bearer ")){  
      return null;  
        }  
      return token.substring(7, token.length());  
    }
O recuperarToken captura o valor do token e retorna um substring partindo da posição 7, eliminando o “Bearer “ e retornando apenas o token. Ao receber a string com o token, o método doFilterInternal deve realizar uma chamada para isTokenValid e caso essa verificação retorne true, ele chama outro método que é o autenticarCliente.

    public void autenticarCliente(String token){  
      Long usuarioId = this.tokenService.getUserId(token);  
        Usuario usuario = this.usuarioRepository.findById(usuarioId).get();  
        UsernamePasswordAuthenticationToken authenticationToken =  
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());  
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);  
    }
Este por sua vez captura o objeto Usuario por meio do id presente no token, gera uma instância de UserNamePasswordAuthenticationToken  passando o objeto Usuario capturado e realiza o setAuthentication passando como parâmetro esta instância.  

Feito isso, faremos com que nossa entidade Usuario implemente a interface UserDetails, sobrescrevendo alguns métodos solicitados, dentre eles os métodos getUsername, o qual retornaremos o atributo cpf, e o getPassword retornando o atributo password, deixando claro que a autenticação será feita através desses dois atributos.

Implementaremos também os AuthInput(dados de entrada) e o OutputToken(retorno).

    public class AuthInput {  
      @JsonProperty("cpf")  
      private String cpf;  
        @JsonProperty("password")  
      private String password;  
      
        public AuthInput(){}  
      public AuthInput(String cpf, String password){  
      this.cpf = cpf;  
            this.password = password;  
        }  
      
      public String getCpf() {  
      return cpf;  
        }  
      
      public String getPassword() {  
      return password;  
        }  
      
      public UsernamePasswordAuthenticationToken convert(){  
      return new UsernamePasswordAuthenticationToken(cpf, password);  
        }  
      
    }

    public class OutputToken {  
      private String token;  
        private String type;  
      
        public OutputToken(String token, String type){  
      this.token = token;  
            this.type = type;  
        }  
      
      public String getToken() {  
      return token;  
        }  
      
      public String getType() {  
      return type;  
        }  
    }
## Controllers

### Controller de Usuário
    

Iniciaremos pelo `controller` de **Usuário**, responsável pelo `endpoint` **“/usuarios”**.

    @PostMapping  
    public ResponseEntity<?> createUsuario(@RequestBody @Valid InputUsuario input,  
                                           UriComponentsBuilder builder){  
      try{  
      OutputUsuario output = this.service.save(input);  
            URI uri = builder.path("/usuarios/{id}").buildAndExpand(output.getId()).toUri();  
            return ResponseEntity.created(uri).body(output);  
        }catch(Exception e){  
      return ResponseEntity.badRequest().body("Verifique as informações e tente novamente");  
        }  
    }

O método invoca o serviço de usuário através do método **save**, caso tudo ocorra como esperado, um `ResponseEntity` é retornado com o código 201(Created) e um objeto com todas as informações esperadas.

### Controller de Autenticação
    

O `controller` de autenticação(**“/auth”**), por sua vez, é responsável por receber os dados de login do usuário e caso tudo ocorra como esperado retornar um objeto do tipo **OutputToken**(com os atributos **token** e **type**).

    @RestController  
    @RequestMapping("/auth")  
    public class AutenticacaoController {  
      
      @Autowired  
      private AuthenticationManager authenticationManager;  
      
        @Autowired  
      private TokenService tokenService;  
      
        @PostMapping  
      public ResponseEntity<?> authenticate(@RequestBody @Valid AuthInput input){  
      UsernamePasswordAuthenticationToken dadosLogin = input.convert();  
                   try{  
      Authentication authentication = authenticationManager.authenticate(dadosLogin);  
                       String token = this.tokenService.generateToken(authentication);  
                       return ResponseEntity.ok(new OutputToken(token, "Bearer "));  
                   }catch(Exception e){  
      return ResponseEntity.badRequest().body("Usuário Inválido, tente novamente!");  
                   }  
		}
     }
O método cria uma instância de `Authentication` e envia como parâmetro para o **generateToken**, caso haja algum erro nesse processo, será retornado para o usuário o código 400 (Bad Request) informando que os dados inseridos referente ao usuário estão inválidos.

### Controller de Veículos
    

Partiremos para o `controller` referente ao **Veiculo**, que possuirá dois métodos implementados: o de listagem e o de cadastro de veículos.

    @PostMapping  
    public ResponseEntity<?> createVeiculo(@RequestBody InputVeiculo input,  
                                                       UriComponentsBuilder builder,  
                                                       @RequestHeader("Authorization") String token){  
      try{  
      Long id = this.tokenService.getUserId(token.substring(POSICAO_BEARER, token.length()));  
                Usuario usuario = this.usuarioRepository.getById(id);  
      
                OutputVeiculo output = this.veiculoService.saveVeiculo(input, usuario);  
                URI uri = builder.path("/veiculos/{id}").buildAndExpand(output.getId()).toUri();  
      
                return ResponseEntity.created(uri).body(output);  
        }catch(Exception e){  
      return ResponseEntity.badRequest()  
      .body(new VeiculoInvalidoException("Erro ao Cadastrar veículo, verifique os campos!").getMessage());  
        }  
    }
O método de cadastro de veículo deverá resgatar o token contido no cabeçalho e extrair o usuário responsável por este token, passando-o para o método **save** juntamente ao `input` enviado pelo usuário, permitindo que o atributo **usuario** da entidade **Veiculo** contenha o usuário logado, indicando que o veículo foi cadastrado por este usuário específico.

    @GetMapping  
    public ResponseEntity<?> listVeiculo(@RequestHeader("Authorization") String token,  
                                         @PageableDefault(sort = "id",  
                                                 direction = Sort.Direction.ASC,  
                                                 page = 0, size = 10)Pageable pageable){  
      if(this.tokenService.isTokenValid(token.substring(7, token.length()))){  
      Page<OutputVeiculo> outputVeiculos = this.veiculoService.getVeiculos(token, pageable);  
            return ResponseEntity.ok(outputVeiculos);  
        }else{  
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Autentique-se e tente novamente!");  
        }  
      
    }
Da mesma forma como feito no método de cadastro do veículo, iremos capturar o token enviado pela requisição, além de validá-lo. Utilizaremos também a paginação para promover uma melhor resposta para o cliente, deixando definidos os padrões de paginação por meio do `@PageableDefault` e receberemos um `Pageable` como parâmetro.

### Funcionamento da Aplicação
    

Em seguida realizaremos o **cadastro** e **autenticação** do usuário, de acordo com as imagens a seguir.![](https://lh4.googleusercontent.com/iwsNLuiS2CPf1k8UcOCYLnM3-BjrZCORYEjZR6ijvcGM-ZU2QzXXKGtqcWrSZKeJMlhffCUd9eJKzq5UzL1QQcBHUlSfQAlvKK528pewNEfCQdWVf1HBOeD3q41zU4K0Nq5OEkHY)

  
![](https://lh3.googleusercontent.com/6LLgx8FyTuoUMpYP7St7Wo9mhpoWwRTUBM3WN40Fdvb4IwPZFkuXNewFnyhP34tGfNHVOuXgjG3myYagLM1KQ3ZB9wbRIiOsgWXVdX8hRGcYk2GHVzbgTcrDd_QvDomfLBraev-a)

Em seguidas iremos adicionar o `header` **Authorization** com “Bearer valorToken”

![](https://lh3.googleusercontent.com/LYRt9SvMFcvPCA7_Vz-gUdXjWU6f74gDMh6FAwY-WQXzjZafLBU-JwYds3IKJXhWdiMaxZuEGXlIIQ2fhgx3w_41fbPAVwUh02AgjehVmWAktaWdZmjAksnlEuj8396OGcErxGzR)

E então realizar o cadastro e a listagem de um Veículo.![](https://lh5.googleusercontent.com/7VWnYCkxsqby-IjB_7ZK4E1-STX-nlru5ebPZfXuCM3PFE3vrk7zpPJyq-lQK6Y9WpyconQkzJhM27sBHZYWcV7i4R5yCmfVj3LiFEbvDZeHHx4aSlSHuu-T68nl-nlRgOaq-bRU)

![](https://lh6.googleusercontent.com/3bTtEN46eD9SMFgbdT_tzxKqoLfblsk8C-5ppSaEHf6Pm4ElHvX65swMdtEg7JedP_erBxcsYdlm1N3lfGGsyCwmCuKuONq482CwO8QaTDJ5uGrkIBmzum8t8zmwo8RydmpIii_p)

Podemos ver que o veículo listado pertence ao usuário cadastrado. Veremos a seguir a resposta caso algum dos campos sejam preenchidos de forma incorreta.![](https://lh4.googleusercontent.com/w9jXQEc2kw1PNmNUP0dIKPMCt4kUGeGGZjrzYqeNQMki87oLRDjMuPKDdQZ5i6fpoenoiVc1X_aYx8dpzjt5rCGSeENNmnCsc1MQ13zaR2Vl5wRgy8bx-2X0EAfd6wk1L6tDhZsV)

  

Eu, Caio Freitas Caminha, agradeço a sua leitura!

  
  

Contatos:

  

[caminhacaiopro@gmail.com](mailto:caminhacaiopro@gmail.com) (85) 99869-8856

