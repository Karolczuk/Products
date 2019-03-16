
package MK.service;

import MK.dto.CategoryDto;
import MK.dto.ProductDto;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.*;

import java.util.*;
import java.util.stream.Collectors;

public class StreamOperation {

    ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
    private CustomerOrderRepositoryImpl customerOrderRepository = new CustomerOrderRepositoryImpl();
    private GuaranteeRepositoryImpl guaranteeRepository = new GuaranteeRepositoryImpl();
    private CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();
    private ModelMappers modelMappers = new ModelMappers();

    public Map<Category, Product> createMapCategoryTheMostExpesiveProduct() {
        List<Product> products = productRepository.findAll();


        return products
                .stream()
                .collect(Collectors.groupingBy(s -> s.getCategory()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()
                        .stream()
                        .sorted(Comparator.comparing(s -> s.getPrice(), Comparator.reverseOrder()))
                        //.collect(Collectors.toList());
                        .findFirst()
                        .orElseGet(null)
                ));
    }


    /*
    b. Pobranie z bazy danych listy wszystkich produktĂłw, ktĂłre zamawiane
byĹ‚y przez klientĂłw pochodzÄ…cych z kraju o nazwie podanej przez
uĹĽytkownika i wieku z przedziaĹ‚u okreĹ›lanego przez uĹĽytkownika.
Produkty powinny byÄ‡ posortowane malejÄ…co wedĹ‚ug ceny. Informacja
zawiera nazwÄ™ produktu, cenÄ™ produktu, kategoriÄ™ produktu, nazwÄ™
producenta oraz kraj w ktĂłrym wyprodukowano produkt.
     */

    public void pokazProdukty() {
        Scanner sc = new Scanner(System.in);
        String country = sc.nextLine();
        int ageMin = sc.nextInt();
        int ageMax = sc.nextInt();

        List<Product> products = productRepository.findAll();
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();

        List<CustomerOrder> customerOrderList = products.stream()
                .filter(s -> s.getProducer().getCountry().equals(country))
                // .sorted(Comparator.comparing(s -> s.getPrice(), Comparator.reverseOrder()))
                .flatMap(s -> s.getCustomerOrders()
                        // .stream()
                        //    .collect(Collectors.toList())
                        .stream()
                        .filter(p -> p.getCustomer().getAge() >= ageMin && p.getCustomer().getAge() <= ageMax)
                )
                .collect(Collectors.toList());

        System.out.println(customerOrderList);
    }

//    Pobranie z bazy danych listy produktĂłw, ktĂłre obejmuje gwarancja i
//    ktĂłre w ramach gwarancji majÄ… zapewnione usĹ‚ugi o nazwach podanych
//    przez uĹĽytkownika. Pogrupuj te produkty wedĹ‚ug kategorii.

    public Map<CategoryDto, List<ProductDto>> productsWithGuaranteeServices(EGuarantee ... guarantees) {
        return productRepository
                .findAll()
                .stream()
                .filter(p -> p.getGuarantees().containsAll(Arrays.asList(guarantees)))
                .map(modelMappers::fromProductToProductDto)
                .collect(Collectors.groupingBy(ProductDto::getCategoryDto));
    }

    /*
    d. Pobranie z bazy danych listy sklepĂłw, ktĂłre w magazynie posiadajÄ…
produkty, ktĂłrych kraj pochodzenia jest inny niĹĽ kraje, w ktĂłrych
wystÄ™pujÄ… oddziaĹ‚y sklepu. e
     */
    public void d() {
        List<Shop> shops = shopRepository.findAll();

        shops.stream()
                .peek(s -> s.getStocks()
                        .stream()
                        .filter(ss -> !ss.getProduct().getProducer().getCountry().equals(s.getCountry())))
                .collect(Collectors.toList());
    }



    /*
    e. Pobranie z bazy danych producentĂłw o nazwie branĹĽy podanej przez
uĹĽytkownika, ktĂłrzy wyprodukowali produkty o Ĺ‚Ä…cznej iloĹ›ci sztuk
wiÄ™kszej niĹĽ liczba podana przez uĹĽytkownika.
*/

    // napisac osobna metode, ktora dla kazdego producenta pobiera ile
    // wyprodukowal produktow - pamietaj musisz uwzglednic ilosci produktow aktualnie
    // ktore masz w stocku
    public void ee(String name, int ile) {
        List<Producer> producers = producerRepository.findAll();

        producers
                .stream()
                .filter(p -> p.getTrade().getName().equals(name) && metoda_ile_sztuk(p) >= ilosc_usera)
                .collect(Collectors.toList());


        List<Stock> stocks = stockRepository.findAll();

        stocks.stream()
                .filter(s -> s.getProduct().getProducer().equals(name))
                .filter((m1, m2) -> (Integer.sum(m1, m2)))
                .map(s -> s.getQuantity())
                .reduce(0, (s1, s2) -> s1 + s2);



    /*

f. Pobranie z bazy danych zamĂłwieĹ„, ktĂłre zĹ‚oĹĽono w przedziale dat
pobranym od uĹĽytkownika o kwocie zamĂłwienia (po uwzglÄ™dnieniu
zniĹĽki) wiÄ™kszej niĹĽ wartoĹ›Ä‡ podana przez uĹĽytkownika.
 */

 /*
 g. Pobranie z bazy danych listy produktĂłw, ktĂłre zamĂłwiĹ‚ klient o
imieniu, nazwisku oraz nazwie kraju pochodzenia pobranych od
uĹĽytkownika. Produkty naleĹĽy pogrupowaÄ‡ ze wzglÄ™du na producenta,
ktĂłry wyprodukowaĹ‚ produkt.
*/

        public void g () {
            String name;
            String surname;
            String country;

            List<Product> products = productRepository.findAll();
            List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        }



/*
h. Pobierz z bazy danych listÄ™ tych klientĂłw, ktĂłrzy zamĂłwili
przynajmniej jeden produkt pochodzÄ…cy z tego samego kraju co klient.
Informacje o kliencie powinny zawieraÄ‡ imiÄ™, nazwisko, wiek, nazwÄ™
kraju pochodzenia klienta oraz iloĹ›Ä‡ zamĂłwionych produktĂłw
pochodzÄ…cych z innego kraju niĹĽ kraj klienta
     */


        public void hh () {
            List<Customer> customers = customerRepository.findAll();
            List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
//        customers
//                .stream()
//                .peek(s->s.getCustomerOrders().stream().filter(c))
            // .filter(f -> f.getCountry().equals(customerOrders.stream().filter(s -> s.findAll().)))


        }


    }
}

