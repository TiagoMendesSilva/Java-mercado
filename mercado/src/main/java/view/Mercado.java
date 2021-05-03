package view;

import helper.Utils;
import jdk.jshell.execution.Util;
import model.Produto;

import java.util.*;

public class Mercado {

    private static Scanner teclado = new Scanner(System.in);

    private static ArrayList<Produto> produtos;

    //Map é uma interface
    private static Map<Produto,Integer> carrinho;

    public static void main(String[] args){

        produtos = new ArrayList<Produto>();

        //HasMap é uma classe que implementa o Map
        carrinho = new HashMap<Produto, Integer>();
        Mercado.menu();

    }
    private static void menu() {
        System.out.println("========================================");
        System.out.println("============== Bem-vindo(a)=============");
        System.out.println("============== Geek Shop ===============");
        System.out.println("========================================");

        System.out.println("Selecione uma opção abaixo: ");
        System.out.println("1 - Cadastrar produto");
        System.out.println("2 - Listar produtos");
        System.out.println("3 - Comprar produto");
        System.out.println("4 - Visualizar carrinho");
        System.out.println("5 - Sair do sistema");

        int opcao = 0;
        try{
            opcao = Integer.parseInt(Mercado.teclado.nextLine());
        } catch(InputMismatchException e){
            Mercado.menu();
        } catch(NumberFormatException f){
            Mercado.menu();
        }

        switch(opcao){
            case 1:
                Mercado.cadastrarProduto();
                break;
            case 2:
                Mercado.listarProdutos();
                break;
            case 3:
                Mercado.comprarProduto();
                break;
            case 4:
                Mercado.visualizarCarrinho();
                break;
            case 5:
                System.out.println("Volte sempre!");
                Utils.pausar(2);
                System.exit(0);
                default:
                    System.out.println("Opção invãlida.");
                    Utils.pausar(2);
                    Mercado.menu();
                    break;
        }
    }

    private static void cadastrarProduto(){
        System.out.println("Cadastro de Produto");

        System.out.println("Informe o nome do produto: ");
        String nome = Mercado.teclado.nextLine();

        System.out.println("Informe o preço do produto: ");
        Double preco = Mercado.teclado.nextDouble();

        Produto produto = new Produto(nome, preco);

        Mercado.produtos.add(produto);

        System.out.println("O produto " + produto.getNome() + " foi cadastrado com sucesso.");
        Utils.pausar(2);
        Mercado.menu();
    }
    private static void listarProdutos(){

        if( Mercado.produtos.size() > 0){
            System.out.println("Listando produtos: ");
            System.out.println("");

            for(Produto p : Mercado.produtos){
                System.out.println(p);
            }
        }else{
            System.out.println("Ainda não tem nenhum produto cadastrado.");
        }
        Utils.pausar(2);
        Mercado.menu();
    }
    private static void comprarProduto() {

        if(Mercado.produtos.size() > 0){
            System.out.println("Informe o código do produto que deseja comprar: ");
            System.out.println();
            System.out.println("========== Produtos disponíveis: ==========");
            for(Produto p : Mercado.produtos){
                System.out.println(p);
                System.out.println("--------------------");
            }
            int codigo = Integer.parseInt(Mercado.teclado.nextLine());
            boolean tem = false;

            for(Produto p : Mercado.produtos) {
                if (p.getCodigo() == codigo) {
                    int qtde = 0;
                    try {
                        qtde = Mercado.carrinho.get(p);
                        //Se já existir produto no carrinho
                        Mercado.carrinho.put(p, qtde + 1);
                    } catch (NullPointerException e) {
                        //Primeiro produto no carrinho
                        Mercado.carrinho.put(p, 1);
                    }
                    System.out.println("O produto " + p.getNome() + " foi adicionado no carrinho.");
                    tem = true;

                    if (tem) {
                        System.out.println("Deseja adicionar produtos no carrinho? ");
                        System.out.println(" 1 - Sim ou 0 - Não");

                        int opcao = Integer.parseInt(Mercado.teclado.nextLine());
                        if (opcao == 1) {
                            Mercado.comprarProduto();
                        } else {
                            System.out.println("Aguarde enquanto fechamos o seu pedido: ");
                            Utils.pausar(2);
                            Mercado.fecharPedido();
                        }
                    } else {
                        System.out.println("O produto não foi encontrado pelo código " + codigo);
                        Utils.pausar(2);
                        Mercado.menu();
                    }
                }
            }

        }else{
            System.out.println("Ainda não tem nenhum produto cadastrado no mercado.");
            Utils.pausar(2);
            Mercado.menu();
        }



    }

    private static void visualizarCarrinho(){
        if(Mercado.carrinho.size() > 0){
            System.out.println("Produtos no carrinho: ");
            //Para cada produto no nosso carrinho
            for(Produto p : Mercado.carrinho.keySet()){
                System.out.println("Produto: " + p + "\nQuantidade: " + Mercado.carrinho.get(p));

            }
        }else{
        System.out.println("Ainda não há produtos no carrinho.");
        }
        Utils.pausar(2);
        Mercado.menu();
    }

    private static void fecharPedido(){
        Double valorTotal = 0.0;
        System.out.println("Produtos no carrinho");

        for(Produto p : Mercado.carrinho.keySet()){
            int qtde = Mercado.carrinho.get(p);
            valorTotal += p.getPreco() * qtde;
            System.out.println("Produto: " + p.getNome() + "\nQuantidade: " + qtde );
            System.out.println("-------------------");
        }
        System.out.println("Sua fatura é " + Utils.doubleParaString(valorTotal));
        Mercado.carrinho.clear();
        System.out.println("Obrigado pela preferência.");
        Utils.pausar(5);
        Mercado.menu();
    }
}
