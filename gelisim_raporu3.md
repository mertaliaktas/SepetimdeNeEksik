### Düzeltmeler

Bu aşamada Java olarak geliştirdiğimiz projeyi Android Studio'ya taşıdık. Projenin devamında Android uygulama olacak şekilde düzenledik. Madencilik yapacağımız veritabanını değiştik. Önceden tüm veritabanını tararken şimdi ise sadece ürünlerin geçtiği kayıtların oluşturduğu veritabanını tarıyoruz. "findConfidence" metodunun içinde,okutulan ürünlere göre en faydalı ürünü bulmak için  gerekli kodlar eklendi. Android kısmında bir ürün taratmak için bir tara butonu koyduk ve bu butona tıkladığımızda barkod tarama ekranı geliyor. Eğer taratılan ürün veritabanında varsa ismini listviewda gösteriyor, yoksa taratılan ürün veritabanında yok diye bir uyarı veriyor. Taratılan bir ürün silinmek istendiğinde üzerine uzun tıklanıp silinebilsin veya vazgeçilsin diye bir menu klasörüne xml dosyası ekledik. Ayrıca listede ki tüm ürünleri tek seferde silmek içinde bir sil butonu koyduk. Taratılan ürün(ler)e uygun ürün sonucunu bir layout oluşturduk ve diğer bir ekranda göstermesini sağladık. Gösterdikten sonra geri ana sayfaya dönmek için ise bir sıfırlama butonu koyduk. Threshold (eşik değer) için ana sayfada bir edittext koyduk. Hesapla butonuna bastığımızda hesaplama süresince işleminiz ediyor şeklinde bir progressDialog kullandık. 

### Kaynaklar



### Zorluklar

Daha hızlı ve maliyetsiz madencilik yapabilmek için tüm veritabanını ağaca yerleştirmemek gerekiyor. Güveni bulunmak istenen ürünleri input olarak verirken başlık tablosundaki sıralamaya göre sıralayıp algoritmaya vermek gerekiyor. Java sınıflarını android de nasıl kullancağımız konusunu android studio da sınıflar oluşturarak kodlarımızı her birinin kendi içine attık. Sadece main sınıfını android de MainActivity kısmında hesaplama kısmına attık.  Android kısmında taratılan verileri bir yerde tutmamız gerekiyordu. Bunu bir listede tutarak çözdük. Ayrıca veritabanını kaybolmadan ve kullanıcının erişemeden kullanılabilmesi için ise bir asset klasörü oluşturduk ve veritanımızı bu klasöre attık. Diğer dosyalama sistemlerini kullanmamamızın sebebi ise her cihazda o anda oluşturuluyor ve bu yüzden veritabanı sıfırlanıyor olması. 

### Araçlar

Android Studio, Eclipse, ZXing Barcode Scanner.
