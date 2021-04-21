### Düzeltmeler

Bu aşamada Java olarak geliştirdiğimiz projeyi Android Studio'ya taşıdık. Projenin devamında Android uygulama olacak şekilde düzenledik. Madencilik yapacağımız veritabanını değiştik. Önceden tüm veritabanını tararken şimdi ise sadece ürünlerin geçtiği kayıtların oluşturduğu veritabanını tarıyoruz. "findConfidence" metodunun içinde,okutulan ürünlere göre en faydalı ürünü bulmak için  gerekli kodlar eklendi. Android kısmında bir ürün taratmak için bir "Tara" butonu koyduk ve bu butona tıkladığımızda barkod tarama ekranı geliyor. Eğer taratılan ürün veritabanında varsa ismini "listview"'da gösteriyor, yoksa taratılan ürün veritabanında yok diye bir uyarı veriyor. Taratılan bir ürün silinmek istendiğinde üzerine uzun tıklanıp silinebilsin veya vazgeçilsin diye "menu" klasörüne xml dosyası ekledik. Ayrıca listedeki tüm ürünleri tek seferde silmek içinde bir "Sil" butonu koyduk. Taratılan ürün(ler)e uygun ürün sonucunu bir "Layout" oluşturduk ve diğer bir ekranda göstermesini sağladık. Gösterdikten sonra geri ana sayfaya dönmek için ise bir sıfırlama butonu koyduk. Threshold (eşik değer) için ana sayfada bir "Edittext" koyduk. Hesapla butonuna bastığımızda hesaplama süresince "İşleminiz Devam Ediyor..." şeklinde bir "progressDialog" ekledik. 

### Kaynaklar

- Programmer World. "How to create your own QR Code and Barcode scanner reader Android App?". Youtube. 26 Ocak 2020.
- Alper Beyler. "Android ProgressDialog ve Progressbar Kullanımı". mobilhanem.com . 9 Kasım 2018.
- Ying Wang. "Fruithut Dataset". philippe-fournier-viger.com . 2020

### Zorluklar

Daha hızlı ve maliyetsiz madencilik yapabilmek için tüm veritabanını ağaca yerleştirmemek gerekiyor. Güveni bulunmak istenen ürünleri input olarak verirken başlık tablosundaki sıralamaya göre sıralayıp algoritmaya vermek gerekiyor. Java sınıflarını Android Studio'da nasıl kullancağımız, nerede çalıştıracağımız ve nasıl değer döndüreceğimiz konusunda zorluk yaşadık. Bu sorunu, Android Studio'da sınıflar oluşturarak kodlarımızı metodlara bölüp, ait ollduğu sınıfın içine attık. Sadece main sınıfını android de MainActivity kısmında hesaplama kısmına attık. Android kısmında taratılan verileri bir yerde tutmamız gerekiyordu. Bunu bir listede tutarak çözdük. Ayrıca veritabanını kaybolmadan ve kullanıcının veritabanına dışarıdan erişemeden kullanılabilmesi için ise bir "asset" klasörünü kullandık ve veritabanımızı bu klasöre attık. Diğer depolama alanlarını kullanmamamızın sebebi ise "asset" klasörü her cihazda o anda oluşturuluyor ve bu yüzden veritabanının sıfırlanıyor yani erişilemiyor. 

### Araçlar

Android Studio, Eclipse, ZXing Barcode Scanner.
