## 4Bank - API Spring-Java
<img src="./logo.png" width="82" height="44"/>

---

### Requisitos - Deve ser possível...

- [x]...criar/Listar Agência
- [x]...criar/Listar Cliente
- [x]...pesquisar Cliente por CPF
- [x]...pesquisar Conta Corrente por CPF
- [x]...cadastrar/Listar Conta Solidária
- [x]...obter balanço geral do cliente (todas as contas do cliente)
- [x]...obter balanço de cada conta do cliente
- [x]...sacar
- [x]...depositar
- [x]...transferir
- [x]...filtrar contas por CPF
- [x]...gerar extratos por operação
- [x]...listar extratos por cliente

---

## Regras de negócio - Não deve ser possível...

- [x]...cadastrar um cliente com o mesmo CPF
- [x]...cadastrar um cliente em uma agência inexistente
- [x]...cadastrar uma conta em um cliente inexistente
- [x]...cadastrar uma ContaSolidária de uma conta inexistente
- [x]...transferir/depositar/sacar para uma conta inexistente 
- [x]...sacar/transferir com saldo insuficiente
- [x]...ter uma conta duplicada em ContasSolidárias
- [x]...cadastrar um cliente com cpf inválido
