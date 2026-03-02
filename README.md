Nama: Nisriina Wakhdah Haris<br>
NPM: 2406360445<br>
Kelas: ProLan - A<br>

<details>
<Summary><b>Refleksi 1</b></Summary>
Berikut ini adalah clean code principles yang telah saya gunakan dalam mengimplementasikan dua fitur baru menggunakan
Spring Boot, yaitu:

1. Meaningful names: Dalam membuat kode untuk mengimplementasikan kedua fitur tersebut saya menggunakan prinsip meaningful
names. Tujuannya adalah agar saya tidak lupa mengenai fungsi variabel yang telah saya buat, misalnya variabel product pada
method deleteById yang fungsinya untuk menyimpan suatu produk
2. Function: Saya juga membuat function untuk mengimplementasikan setiap fitur yang ada. Tujuannya adalah agar kode untuk
suatu fitur tidak bercampur dengan kode fitur lain dan agar dapat dipakai berulang kali. Selain itu, dalam membuat function
saya menggunakan nama yang menggambarkan perilaku function tersebut, seperti function update yang fungsinya untuk mengedit (update)
product yang telah dibuat
3. Comment: Saya menambahkan beberapa komen yang menjelaskan secara singkat mengenai apa yang dilakukan oleh suatu function
agar orang lain yang membaca kode saya mengerti tujuan suatu function dibuat

Saya juga menerapkan secure coding practice berupa input data validation. Validasi inii diterapkan pada file createProduct.html
dan editProduct.html dengan menambahkan atribut required pada field productName dan productQuantity agar field tersebut
tidak bisa kosong. Pada createProduct.html saya menambahkan ketentuan min="1" input productQuantity, karena saat user
membuat prduct baru, jumlah stok minimalnya yang diizinkan adalah 1 dan tidak bisa nol. Selain itu, pada editProduct.html saya menambahkan ketentuan
min="0" pada input field productQuantity karena stok barang yang telah terdaftar di dalam list produk bisa saja habis (bernilai nol) 
dan saya menambahkan anotasi validasi seperti @NotBlank dan @Min pada model untuk menambahkan validasi input di sisi backend
</details>

<details>
<Summary><b>Refleksi 2</b></Summary>

1. Setelah membuat unit test, saya merasa bahwa unit test itu sangat penting dalam membuat suatu program, karena dengan adanya unit test
kita dapat mengetahui apakah program yang kita buat dapat berjalan dengan baik tanpa error dan tidak cukup apabila jika hanya menjalankan kodenya saja tanpa
menguji setiap function. Dalam suatu class, jumlah unit test yang perlu dibuat tidak dapat ditentukan secara pasti, karena tergantung pada behavior dari function yang telah kita buat. 
Hal ini dikarenakan, setiap function dapat memiliki lebih dari satu unit test untuk menguji positive case dan negative case. Unit test dapat dianggap cukup ketika 
seluruh behavior dari program telah diuji, termasuk positive dan negative case. Salah satu indikator yang dapat digunakan adalah code coverage, namun faktor yang paling 
penting adalah unit test akan gagal apabila behavior dari program salah. Apabila suatu unit test menghasilkan 100% code coverage, tidak menjamin bahwa kode 
tersebut bebas dari bug dan error. Hal ini dikarenakan, code coverage hanya menunjukkan bawa seluruh kode telah dijalankan oleh unit test, namun tidak menjamin bahwa 
semua behavior telah diuji dengan benar, contohnya adalah ketika terdapat bug yang berasal dari desain suatu file html, saat unit test dijalankan code coveragenya tetap 100% 
namun pada paraktiknya tetap terdapat bug. Oleh karena itu, code coverage tidak bisa dijadikan sebagai jaminan bahwa program bbebas dari bug dan error, hanya saja dapat 
meningkatkan kepercayaan bahwa program dapat bekerja sesuai dengan yang diharapkan.
<br>
2. Pembuatan functional test suite baru yang memiliki setup procedures dan variabel yang sama dengan functional test sebelumnya dapat menurunkan 
kualitas dan kebersihan kode. Hal ini disebabkan karena adanya duplikasi kode yang melanggar prinsip DRY (Don't Repeat Yourself)
sehingga dapat menimbulkan masalah, seperti meningkatkan risiko inkonsistensi kode dan apabila terdapat perubahan, maka perubahan tersebut harus dilakukan di banyak tempat. 
Selain itu, masalah lain yang dapat muncul adalah kesulitan dalam pemeliharanan kode ketika jumlah test bertambah karena terdapat duplikasi kode(menurukan maintanability kode) 
serta dapat membuat seorang programmer kesulitan dalam membaca dan memahami kode karena terdapat kode yang sama dan berulang (menurunkan readability kode).
Solusi yang dapat dilakukan untuk menghindari permasalahan tersebut adalah memisahakan antara kode setup dengan kode pengujian beharivor yang spesifik,
seperti membuat base functional test class yang berisi setup umum agar test suite lain bisa mewarisi (inherit) dari base class tersebut dan mengimplementasikan
kode pengujian sesuai dengan behavior yang akan diuuji, dengan begitu kode menjadi lebih bersih, mudah dirawat, rapih dan terstruktur
</details>

<details>
<Summary><b>Refleksi 3 (Modul 2)</b></Summary>

1. Berikut ini adalah code quality issues yang saya hadapi ketika mengerjakan tutorial ini, kebanyakan merupakan issues mengenai maintainability code:
    1. Field injection<br>
        Masalah ini terjadi karena terdapat kode yang menggunakan @Autowired, seperti:<br>
       @Autowired<br>
       private ProductService service;<br>
        Strategi: cara saya menangani masalah ini adalah dengan menghapus @Autowired dan menggantikannya dengan membuat constructor (constructor injection) dan mengubah variabel di atas menjadi private final ProductService service
    2. Test tanpa assertion<br>
         File EshopApplicationTest.java berisi kode mengenai unit-test untuk aplikasi Eshop. Namun pada method yang mengetes method main tidak ditambahkan assertion sehingga kode tersebut tidak memverifikasi behavior yang ingin di-test<br>
         Strategi: Mengubah blok kode<br>
         @Test
         void runMain() {
             System.setProperty("server.port", "0");
             assertDoesNotThrow( () ->
                 EshopApplication.main(new String[] {}));
         }<br>
         Menjadi sebagai berikut dengan menambahkan assertion<br>
       @Test
       void runMain() {
           System.setProperty("server.port", "0"); 
           assertDoesNotThrow( () ->
             EshopApplication.main(new String[] {}));
       }
    3. 'public' modifier pada kelas unit-test<br>
       Masalah ini terjadi karena saya menggunakan modifier 'public' pada semua kelas unit-test padahal sebenarnya tidak perlu 'public'. Hal ini dikarenakan, Sonar menganggap public di test class sebagai unnecessary modifier (berlaku sejak JUnit 5)<br>
        Strategi: Menghapus modifier 'public' pada semua test class
   4. Unused dependency dan import<br>
        Di beberapa file unit-test saya memasukkan cukup banyak import dan dependency yang saya kira akan digunakan padahal kenyataannya kode tersebut tidak digunakan sehingga menurunkan maintainability code karena terdapat dead code<br>
        Strategi: Menghapus import dan dependency yang tidak diperlukan sehingga tidak ada dead code atau unused import
   5. throws Exception pada method test<br>
        Ketika saya membuat kelas yang berisi functional test, saya memasukkan throws Exception pada method test tersebut sehingga menimbulkan masalah maintainability code karena pada method tersebut tidak memiliki checked exception (hanya tulisan throws Exception saja )<br>
        Strategi: Menghapus tulisan throws Exception untuk menghindari generic exception declaration
   6. Method @BeforeEach yang kosong<br>
        Saya membuat method setUp yang kosong di dalam file unit-test<br>
      @BeforeEach<br>
      void setUp() {<br>
      }<br>
        Sehingga menimbulkan masalah karena methodnya kosong dan tidak digunakan(dead code)<br>
        Strategi: Menghapus method tersebut untuk menghilangkan dead code
   7. JaCoCo coverage 0% on new code<br>
        Ketika saya push kode yang baru ke Sonar, pengecekannya gagal karena ia menunjukkan 0.0% Coverage on New Code. Penyebabnya adalah karena sonar tidak menemukan file XML coverage<br>
        Strategi: Memperbaiki buil.gradle.kts dengan menambahkan property, seperti  "sonar.coverage.jacoco.xmlReportPaths" dan mengaktifkan xml.required.set(true) untuk memastikan coverage XML sudah tersedia sebelum dicek oleh Sonar
        
2. Berdasarkan CI/CD workflows yang saya miliki, project saya sudah memenuhi konsep Continous Integration dengan baik. Hal ini dikarenakan, workflows yang saya buat sudah menerapkan tests automation setiap kali push atau pull request ketika terdapat perubahan kode. Selain itu, terdapat
    SonarCloud, CodeQL, dan Scorecard yang berfungsi untuk menjalankan pemeriksaan keamanan dan analisis kode secara otomatis.
    Project ini juga sudah memenuhi konsep Continous Deployment karena sudah terhubung dengan Koyeb yang akan melakukan auto deploy setiap kali terdapat perubahan pada branch main dengan syarat semua proses CI harus berhasil, sehingga kita tidak perlu melakukan deployment secara manual.
    Oleh karena itu, implementasi saat ini sudah sesuai dengan definisi Continous integration dan Continous Deployment.
</details>

<details>
<Summary><b>Refleksi 4 (Modul 3)</b></Summary>

1. Berikut ini merupakan SOLID principle yang saya gunakan, yaitu:<br>
    **a. Single Responsibility Principle:** Prinsip ini mengatakan bahwa setiap class memiliki satu tanggung jawab utama, contoh penerapannya adalah:<br>
        1. Memisahkan CarController menjadi class indpenden tanpa extends ProductController karena melanggar SRP pada level desain yang disebabkan oleh penggunaan inheritance yang tidak tepat<br>
        2. Memindahkan pembuatan UUID pada CarRepository ke CarService agar repository hanya berfokus pada penyimpanan data<br>
        3. Membuat interface Read dan CRUD untuk repository dan service agar setiap interface bisa fokus pada tanggung jawabnya masing-masing.<br>
    <br>
    **b. Open-Closed Principle:** Mengatakan bahwa Software entities (classes, module, function) harus terbuka terhadap ekstensi, namun tertutup untuk modifikasi, sehingga
        penambahan fitur baru tidak mengharuskan perubahan pada kode yang sudah stabil. Contoh penerapannya adalah:<br>
        1. Membuat interface CarRepository agar dapat membuat berbagai cara penyimpanan hanya dengan membuat class baru yang mengimplementasikan interface tersebut<br>
        2. Service bergantung pada interface CarRepository agar CarServiceImpl tidak perlu berubah jika implementasi repository ada yang diubah<br>
   **c. Liskov Substitution Principle:** Mengatakan bahwa objek subclass harus dapat menggantikan objek induknya tanpa mengubah perilaku yang ada. Contoh penerapannya adalah
        Membuat CarController menjadi class utuh yang berdiri sendiri tanpa meng-extend sebuah class karena CarController bukan tipe khusus dari ProductController sehingga apabila terdapat method pada superclass yang berubah atau memiliki behaviour 
        tertentu, maka CarController bisa mewarisi sesuatu yang tidak relevan tersebut.<br>
   **d. Interface Segregation Priciple:** Mengatakan bahwa sebuah class tidak boleh dipaksa untuk mengimplementasikan method yang tidak dibutuhkannya. Oleh karena itu, pembuatan interface harus dirancang secara spesifik agar class 
        tidak perlu mengimplementasikan method yang tidak digunakan. Contoh penerapannya adalah membuat interface Read dan CRUD yang kecil dan memiliki tugas spesifik pada repository dan service<br>
   **e. Dependency Inversion Principle:** Mengatakan bahwa sebuah class sebaiknya bergantung pada interface atau abstract class daripada bergantung pada concrete class 
        1. Mengubah private CarServiceImpl carService; pada CarController menjadi private  private CarService carService agar sesuai dengan prinsip DIP.<br>
        2. Membuat interface bernama CarRepository agar CarServiceImpl bergantung pada interface<br>
        3. Mengubah private CarRepositoryImpl carRepository; menjadi private CarRepository carRepository pada CarServiceImpl<br>
<br><br>
2. Berikut ini adalah keutungan dalam mengimplementasikan SOLID principle, yaitu:<br>
    **a. Single Responsibility Principle<br>**
        - Memisahkan logika controller untuk Car dan Product agar setiap controller milik kedua model tersebut tidak bercampur dan tidak saling bergantung satu sama lain, dan setiap controller memiliki tanggung jawab sendiri-sendiri<br>
        - Memisahkan tanggung jawab setiap interface agar dapat fokus pada tugas utamanya<br>
    **b. Open-Closed Principle<br>**
        - Meminimalisir munculnya bug baru pada kode yang sudah stabil, ex: Jika terdapat perubahan pada CarRepository, CarServiceImpl tidak perlu diubah juga<br>
        - Memudahkan unit testing, ex: Ketika mengetes CarServiceImpl, tidak perlu mengetes interface yang berhubungan dengannya (CarRepository)<br>
    **c. Liskov Substitution Principle<br>**
        - Tidak ada pewarisan method yang tidak relevan, testing lebih aman, dan perilaku setiap controller lebih konsisten dan sesuai dengan tugasnya.<br>
    **d. Interface Segregation Principle<br>**
        - Meningkatkakan fleksibilitas dalam implementasi<br>
        - Class yang mengimplementasikannya tidak perlu menuliskan metode yang tidak digunakan, ex: class ShowCarCatalog yang bertugas untuk menampilkan daftar mobil
        cukup menggunakan CarRead dan tidak perlu menuliskan method delete() dan update()<br>
    **e. Dependency Inversion Principle**<br>
        - Mengurangi ketergantungan kuat CarController karena ia tidak lagi terikat pada implementasi spesifik, sehingga apabila terdapat perubahan pada CarServiceImpl maka controller tidak perlu diubah, mempermudah unit testing, meningkatkan maintainability
          karena perubahan pada implementasi service tidak memengaruhi controller<br>
<br>
3. Berikut ini merupakan kerugian jika tidak mengimplementasikan SOLID principle, yaitu:<br>
    **a. Single Responsibility Principle<br>**
        - Kerugian: <br>
    **b. Open-Closed Principle<br>**
        - Jika ada perubahan pada concrete class, maka class yang meng-extend juga harus ikut diubah, ex: Jika mengubah CarRepositoryImpl, maka isi CarServiceImpl juga dipaksa untuk diubah<br>
        - Terdapat keterikatan kuat antar class, ex: Jika terdapat perubahan pada CarRepositoryImpl, maka CarServiceImpl juga akan terkena dampaknya<br>
    **c. Liskov Substitution Principle<br>**
        - Menciptakan inheritance yang tidak valid dan membingungkan, perubahan pada ProductController dapat merusak CarController, dan sulit untuk dikembangkan. Contoh: ProductController memiliki method bernama editProductPage(), maka CarController otomatis
          akan mewarisi menthod tersebut padahal CarController hanya bertugas untuk mengurus Car saja, bukan Product.<br>
    **d. Interface Segregation Principle<br>**
        - Meningkatkan risiko tight coupling, ex: class ShowCarCatalog yang mengimplentasikan class CarRepository (sebelum SOLID) harus menuliskan method delete() dan update() padahal class tersebut hanya untuk menampilkan daftar mobil yang ada.<br>
    **e. Dependency Inversion Principle<br>**
        - Jika tidak menerapkan DIP, maka akan terjadi tight coupling antara CarController dan CarServiceImpl karena high-level module bergantung langsung pada low-level modul dan apabila 
        terdapat perubahan pada implementasi service, maka controller juga akan ikut diubah, ex: private CarServiceImpl carService;.  
</details>