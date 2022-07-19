package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int menuChoice;
        int sortChoice;
        BudgetAndSpend budget = new BudgetAndSpend();
        menuChoice = menu();

        while (menuChoice != 0) {
            System.out.println();
            switch (menuChoice) {
                case 1:
                    System.out.println("Enter income:");
                    budget.addIncome(scanner.nextFloat());
                    System.out.println("Income was added!");
                    break;
                case 2:
                    budget.AddPurchase();
                    break;
                case 3:
                    budget.printPurchase();
                    break;
                case 4:
                    System.out.println("Balance: $" + budget.getBalanceStr());
                    break;
                case 5:
                    budget.saveFile();
                    System.out.println("Purchases were saved!");
                    break;
                case 6:
                    budget.loadFile();
                    System.out.println("Purchases were loaded!");
                    break;
                case 7:
                    sortChoice = sortOptions();
                    while (sortChoice < 4) {
                        System.out.println();
                        switch (sortChoice) {
                            case 1:
                                budget.SortAllPurchases();
                                break;
                            case 2:
                                budget.SortByType();
                                break;
                            case 3:
                                budget.sortInType();
                                break;
                        }
                        System.out.println();
                        sortChoice = sortOptions();
                    }
                    break;
                //default:
                    //System.out.println("Invalid option");
                    //break;
            }
            System.out.println();
            menuChoice = menu();
        }

        System.out.println();
        System.out.println("Bye!");
    }

    public static void showmenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchase");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }

    public static int menu() {
        Scanner scanner = new Scanner(System.in);
        showmenu();
        return scanner.nextInt();
    }

    public static int sortOptions() {
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static int purchaseType(boolean Add) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
        if (Add) {
            System.out.println("5) Back");
        } else {
            System.out.println("5) All");
            System.out.println("6) Back");
        }
        return scanner.nextInt();
    }

    public static void AddPurchase(BudgetAndSpend budget) {
        int type = purchaseType(true);
        if (type == 5) {
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
        String purchaseItem = scanner.nextLine();
        System.out.println("Enter its price:");
        float price = scanner.nextFloat();
        budget.addPurchases(purchaseItem, price, type);
        System.out.println("Purchase was added!");
    }
}

class BudgetAndSpend {
    ArrayList<ShoppingItem> shoppingList;
    //ArrayList<Integer> Food;
    //ArrayList<Integer> Clothes;
    //ArrayList<Integer> Entertainment;
    //ArrayList<Integer> Other;
    float spend;
    float[] typeSpend;
    float balance;
    public BudgetAndSpend() {
        this.balance = 0;
        this.spend = 0;
        this.shoppingList = new ArrayList<>();
        this.typeSpend = new float[]{0, 0, 0, 0};
    }
    public void addIncome(float income) {
        this.balance += income;
    }

    public void addPurchases(String item, float price, int type) {
        this.shoppingList.add(new ShoppingItem(item, price, type));
        this.spend += price;
        this.typeSpend[type - 1] += price;
        this.balance -= price;
    }
    public int purchaseType(boolean Add) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        if (Add) {
            System.out.println("5) Back");
        } else {
            System.out.println("5) All");
            System.out.println("6) Back");
        }
        return scanner.nextInt();
    }
    public void AddPurchase() {
        int type = purchaseType(true);
        while (type < 5) {
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter purchase name:");
            String purchaseItem = scanner.nextLine();
            System.out.println("Enter its price:");
            float price = scanner.nextFloat();
            this.addPurchases(purchaseItem, price, type);
            System.out.println("Purchase was added!");
            System.out.println();
            type = purchaseType(true);
        }
    }

    public float getBalance() {
        return this.balance > 0 ? this.balance : 0;
    }
    public String getBalanceStr() {
        return String.format("%.2f", this.getBalance());
    }
    public void printPurchase() {
        if (this.shoppingList.isEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            int type = purchaseType(false);
            while (type < 6) {
                System.out.println();
                switch (type) {
                    case 1:
                        System.out.println("Food:");
                        break;
                    case 2:
                        System.out.println("Clothes:");
                        break;
                    case 3:
                        System.out.println("Entertainment:");
                        break;
                    case 4:
                        System.out.println("Other:");
                        break;
                    case 5:
                        System.out.println("All:");
                        break;
                }
                if (type == 5) {
                    for (ShoppingItem item : this.shoppingList) {
                        System.out.println(item.getItemNameWp());
                    }
                    System.out.println("Total sum: $" + String.format("%.2f", this.spend));
                } else {
                    boolean listEmpty = true;
                    float typespend = 0;
                    for (ShoppingItem item : this.shoppingList) {
                        if (item.getItemType() == type) {
                            listEmpty = false;
                            System.out.println(item.getItemNameWp());
                            typespend += item.getItemPrice();
                        }
                    }
                    if (listEmpty) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        System.out.println("Total sum: $" + String.format("%.2f", typespend));
                    }
                }
                System.out.println();
                type = purchaseType(false);
            }
        }
    }

    public void saveFile() {
        File file = new File("purchases.txt");
        try {
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println(this.balance);
                writer.println(this.spend);
                writer.println(this.typeSpend[0]);
                writer.println(this.typeSpend[1]);
                writer.println(this.typeSpend[2]);
                writer.println(this.typeSpend[3]);
                for (ShoppingItem item : this.shoppingList) {
                    writer.println(item.getItemName());
                    writer.println(item.getItemPrice());
                    writer.println(item.getItemType());
                }
                writer.close();
            } catch (FileNotFoundException err1) {
                System.out.println("PrintWriter found no file");
                err1.printStackTrace();
            }
        } catch(IOException err) {
            System.out.println("An error occurred creating new file");
            err.printStackTrace();
        }
        System.out.println("Purchases were saved!");
    }
    public void loadFile() {
        String itemName;
        float itemPrice;
        int itemType;
        File file = new File("purchases.txt");
        try {
            Scanner reader = new Scanner(file);
            this.balance += reader.nextFloat();
            this.spend += reader.nextFloat();
            this.typeSpend[0] += reader.nextFloat();
            this.typeSpend[1] += reader.nextFloat();
            this.typeSpend[2] += reader.nextFloat();
            this.typeSpend[3] += reader.nextFloat();
            while (reader.hasNext()) {
                reader.nextLine();
                itemName = reader.nextLine();
                itemPrice = reader.nextFloat();
                itemType = reader.nextInt();
                this.shoppingList.add(new ShoppingItem(itemName, itemPrice, itemType));
            }
            reader.close();
            //file.delete();
        } catch (FileNotFoundException err) {
            System.out.println("Reader found no file");
            err.printStackTrace();
        }
    }

    public ArrayList<ShoppingItem> sortAll() {
        return sortAnyList(this.shoppingList);
    }
    public ArrayList<ShoppingItem> sortAnyList(ArrayList<ShoppingItem> anyList) {
        ArrayList<ShoppingItem> sortedAll = anyList;
        //Collections.sort(sortedAll);
        ShoppingItem item;
        for (int i = 0; i < sortedAll.size(); i++) {
            for (int j = sortedAll.size() - 1; j > i; j--) {
                item = sortedAll.get(j);
                if (item.getItemPrice() > sortedAll.get(j - 1).getItemPrice()) {
                    sortedAll.set(j, sortedAll.get(j - 1));
                    sortedAll.set(j - 1, item);
                }
            }
        }
        return sortedAll;
    }
    public ArrayList<ShoppingItem> listType(int type) {
        ArrayList<ShoppingItem> typeList = new ArrayList<>();
        for(ShoppingItem i:this.shoppingList) {
            if (i.getItemType() == type) {
                typeList.add(i);
            }
        }
        return typeList;
    }
    public void printSortedList(ArrayList<ShoppingItem> l) {
        ShoppingItem item;
        float typeSpend = 0;
        for (int i = l.size(); i > 0; i--) {
            item = l.get(i-1);
            System.out.println(item.getItemNameWp());
            typeSpend += item.getItemPrice();
        }
        System.out.println("Total sum: $" + String.format("%.2f", typeSpend));
    }
    public void sortInType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        int type = scanner.nextInt();
        System.out.println();
        ArrayList<ShoppingItem> typeList = this.listType(type);
        if (typeList.isEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            switch (type) {
                case 1:
                    System.out.println("Food:");
                    break;
                case 2:
                    System.out.println("Clothes:");
                    break;
                case 3:
                    System.out.println("Entertainment:");
                    break;
                case 4:
                    System.out.println("Other:");
                    break;
            }
            Collections.sort(typeList);
            this.printSortedList(typeList);
        }
    }
    public void SortAllPurchases() {
        if (this.shoppingList.isEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            System.out.println("All:");
            ArrayList<ShoppingItem> sortedAll = this.sortAll();
            for (ShoppingItem i:sortedAll) {
                System.out.println(i.getItemNameWp());
            }
            System.out.println("Total: $" + String.format("%.2f", this.spend));
        }
    }
    public void SortByType() {
        ArrayList<TypeWSpend> sortT = new ArrayList<>();
        sortT.add(new TypeWSpend("Food",this.typeSpend[0]));
        sortT.add(new TypeWSpend("Clothes",this.typeSpend[1]));
        sortT.add(new TypeWSpend("Entertainment",this.typeSpend[2]));
        sortT.add(new TypeWSpend("Other",this.typeSpend[3]));
        Collections.sort(sortT);
        System.out.println("Types:");
        for (int i = 3; i >= 0; i--) {
            System.out.println(sortT.get(i).getTypeWS());
        }
        System.out.println("Total sum: $" + String.format("%.2f", this.spend));
    }
}

class ShoppingItem implements Comparable<ShoppingItem> {
    String itemName;
    float itemPrice;
    int itemType; //1=Food, 2=Clothes, 3=Entertainment, 4=Other

    public ShoppingItem(String item, float price, int type) {
        this.itemName = item;
        this.itemPrice = price;
        this.itemType = type;
    }
    public String getItemName() {
        return this.itemName;
    }
    public String getItemNameWp() {
        return this.itemName + " $" + String.format("%.2f", this.itemPrice);
    }
    public float getItemPrice() {
        return this.itemPrice;
    }
    public int getItemType() {
        return this.itemType;
    }

    @Override
    public int compareTo(ShoppingItem other) {
        return Float.valueOf(this.itemPrice).compareTo(other.getItemPrice());
    }
}

class TypeWSpend implements Comparable<TypeWSpend> {
    String type;
    float spend;
    public TypeWSpend(String typeName, float typeSpend) {
        this.type = typeName;
        this.spend = typeSpend;
    }
    public String getTypeWS() {
        return this.type + " - $" + String.format("%.2f", this.spend);
    }
    public float getSpend() {
        return this.spend;
    }
    @Override
    public int compareTo(TypeWSpend other) {
        return Float.valueOf(this.spend).compareTo(other.getSpend());
    }
}
        /*
        while (scanner.hasNext()) {
            shoppingItem = scanner.nextLine();
            //System.out.println(shoppingItem);
            shoppingList.add(shoppingItem);
            itemAndPrice = shoppingItem.split("\\$");
            //System.out.println(itemAndPrice[itemAndPrice.length - 1]);
            price = Float.parseFloat(itemAndPrice[itemAndPrice.length - 1]);
            total += price;
        }
        for(String item:shoppingList) {
            System.out.println(item);
        }
         */
