package test.Main;

public class Solution {

}

class Encrypt {
    void encrypt() {
        // 加密
    }
}

class Dao {
    void insert() {
        // 数据库操作
    }
}

interface Adapter {
    void operation();
}

class ConcreteAdapter implements Adapter {
    Encrypt encrypt;
    Dao dao;
    @Override
    public void operation() {
        encrypt.encrypt();
        dao.insert();
    }
}

//class Client {
//    ConcreteAdapter concreteAdapter;
//    void operation() {
//        concreteAdapter.operation();
//    }
//}

abstract class AbstractImageFormat {
    public abstract void format();
}

class JPG extends AbstractImageFormat {
    @Override
    public void format() {
        System.out.println("JPG.format()");
    }
}

class GIF extends AbstractImageFormat {
    @Override
    public void format() {
        System.out.println("GIF.format()");
    }
}

class BMP extends AbstractImageFormat {
    @Override
    public void format() {
        System.out.println("BMP.format()");
    }
}

abstract class AbstractImageFilter {
    protected AbstractImageFormat abstractImageFormat;

    public AbstractImageFilter(AbstractImageFormat abstractImageFormat) {
        this.abstractImageFormat = abstractImageFormat;
    }

    public abstract void filter();
}

class Cutout extends AbstractImageFilter {
    public Cutout(AbstractImageFormat abstractImageFormat) {
        super(abstractImageFormat);
    }
    @Override
    public void filter() {
        abstractImageFormat.format();
        System.out.println("Cutout.filter()");
    }
}

class Blur extends AbstractImageFilter {
    public Blur(AbstractImageFormat abstractImageFormat) {
        super(abstractImageFormat);
    }
    @Override
    public void filter() {
        abstractImageFormat.format();
        System.out.println("Blur.filter()");
    }
}

class Sharpen extends AbstractImageFilter {
    public Sharpen(AbstractImageFormat abstractImageFormat) {
        super(abstractImageFormat);
    }
    @Override
    public void filter() {
        abstractImageFormat.format();
        System.out.println("Sharpen.filter()");
    }
}

//class Client {
//    public static void main(String[] args) {
//        AbstractImageFilter AbstractImageFilter = new Cutout(new JPG());
//        AbstractImageFilter.filter();
//        AbstractImageFilter = new Blur(new GIF());
//        AbstractImageFilter.filter();
//        AbstractImageFilter = new Sharpen(new BMP());
//        AbstractImageFilter.filter();
//    }
//}

class AddressBook {
    void copy() {

    }
}

class Message {
    void copy() {

    }
}

class Photo {
    void copy() {

    }
}

class Phone {
    AddressBook addressBook;
    Message message;
    Photo photo;
    void copyAll() {
        addressBook.copy();
        message.copy();
        photo.copy();
    }
}

class Client {
    Phone phone;
    void backups() {
        phone.copyAll();
    }
}

