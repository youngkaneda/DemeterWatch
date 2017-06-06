# parse-review

# O que é?
Parse-review é uma ferramenta desenvolvida na linguagem java com o propósito de analisar projetos desenvolvidos nesta mesma linguagem.
O principal objetivo da da análise é extrair informações do projeto buscando quebras de confinamento em classes que utilizam o framework JCF 
(Java Collection Framework). Posteriormente, será exibido ao usuário quais métodos causam a quebra do confinamento e alteram o estado do objeto.

# Um exemplo
Iremos tomar estas duas classes como exemplo :

```
// Target Class
class A{
    private List<A> elements;
    public List<A> getElements(){
        return this.elements;
    }
}
// Client Class
class C{
    private A a;
    public void m(){
        a.getElements().add(new A());
    }
}
```

`A` é a classe que possui o método que retorna um objeto pertencente ao framework JCF, o método `getElements()`, `C` é a classe que possui uma instância de `A` e realiza a chamada ao método `getElements`, permitindo que `C` tenha total acesso ao atributo de `A` e possa alterar o seu estado, o que é feito na chamada `a.getElements().add(new A())`, está nítido aqui que ocorreu a quebra do confinamento; e são estes os casos que nossa ferramenta procura identificar e detalhar, segue-se o resultado da análise do exemplo realizada pela ferramenta:

`<A, getElements[], java.util.List<A>, C, m[], add[A]>`

# Como utilizar a ferramenta
Antes de tudo é preciso que você tenha instalado o maven, após instalado vá até a pasta deste projeto e abra o terminal, digite
`mvn clean install` Isso instalará as dependências necessárias para que a ferramenta funcione.
--
