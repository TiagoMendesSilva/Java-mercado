package view;

import model.Produto;

public class Teste {
    public static void main(String[] args){
        Produto ps4 = new Produto("Playstation 4", 1789.44);
        Produto xbox = new Produto("Xbox 360", 2000.50);

        System.out.println(ps4);
        System.out.println(xbox);


    }
}
