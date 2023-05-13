import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToyStore {
    private List<Toy> toys = new ArrayList();
    private WinToy winToy = new WinToy();

    public ToyStore() {
    }

    public void addToy(Toy toy) {
        this.toys.add(toy);
    }

    public Toy winToy() {
        return this.winToy.winToy(this.toys);
    }

    public int getSmallToys() {
        return this.winToy.getSmallToys();
    }

    public int getMediumToys() {
        return this.winToy.getMediumToys();
    }

    public int getBigToys() {
        return this.winToy.getBigToys();
    }

    public void setSmallToys(int small) {
        this.winToy.setSmallToys(small);
    }

    public void setMediumToys(int medium) {
        this.winToy.setMediumToys(medium);
    }

    public void setBigToys(int big) {
        this.winToy.setBigToys(big);
    }

    public void saveToyToFile(Toy toy, String filename) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(toy.toString() + "\n");
            writer.close();
        } catch (IOException var4) {
            System.out.println("Ошибка записи в файл");
        }

    }

    public List<Toy> getToys() {
        return this.toys;
    }

    public void increaseToyQuantity(int toyId, int quantityToAdd) {
        Iterator var3 = this.toys.iterator();

        Toy toy;
        do {
            if (!var3.hasNext()) {
                System.out.println("Игрушка с ID " + toyId + " не найдена в магазине.");
                return;
            }

            toy = (Toy)var3.next();
        } while(toy.getId() != toyId);

        toy.setQuantity(toy.getQuantity() + quantityToAdd);
    }
}
