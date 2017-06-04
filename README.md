# parse-review

# O que é?
Parse-review é uma ferramenta desenvolvida na linguagem java com o propósito de analisar projetos desenvolvidos na mesma linguagem,
onde o foco da análise é extrair informações do projeto buscando quebras de confinamento em classes que utilizam o framework JCF 
(Java Collection Framework) e mostrar ao usuário qual método está causando a quebra do confinamento e alterando o estado do objeto.

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

"A" é a classe que possui o método que retorna um objeto pertencente ao framework JCF, o método "getElements()", "C" é a classe que possui uma instância de "A" e realiza a chamada ao método "getElements", permitindo que "C" tenha total acesso ao atributo de "A" e possa alterar o seu estado, o que é feito na chamada "a.getElements().add(new A())", está nítido aqui que ocorreu a quebra do confinamento; e são estes os casos que nossa ferramenta procura identificar e detalhar, segue-se o resultado da análise do exemplo realizada pela ferramenta:
