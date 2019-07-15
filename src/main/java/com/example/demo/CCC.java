package com.example.demo;

public class CCC {
    public void ccc(){
        // 取得SomeClass的Class實例
        Class c = null;
        try {
            c = Class.forName("com.example.demo.SomeClass");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到指定的類別");
        }
        // 取得ClassLoader
        ClassLoader loader = c.getClassLoader();
        System.out.println(loader);
        // 取得父ClassLoader
        System.out.println(loader.getParent());
        // 再取得父ClassLoader
        System.out.println(loader.getParent().getParent());

        System.out.println("類別名稱：" + c.getName());
        System.out.println("是否為介面：" + c.isInterface());
        System.out.println("是否為基本型態：" + c.isPrimitive());
        System.out.println("是否為陣列：" + c.isArray());
        System.out.println("父類別：" + c.getSuperclass().getName());
    }

    public static void main(String[] args) {
        CCC ccc = new CCC();
        ccc.ccc();
    }
}
