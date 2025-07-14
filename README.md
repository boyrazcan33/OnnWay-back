Rota Optimizasyonu Projesi
Teknolojiler: Java 21, Spring Boot, PostgreSQL, React TypeScript

Hafta 1: Proje Kurulumu

Spring Boot projesi olusturuldu
Nearest Neighbor algoritmasi gelistirildi
Git repository kuruldu
Temel proje yapisi hazirlandi

Dosyalar:

Customer.java - Musteri modeli
DistanceCalculator.java - GPS mesafe hesaplama
RouteOptimizer.java - Rota algoritmasi
RouteController.java - API endpoint

Hafta 2: Veritabani Entegrasyonu

PostgreSQL veritabani kuruldu
JPA entityler olusturuldu
CRUD APIler gelistirildi
Database baglantisi yapildi

Dosyalar:

CustomerRepository.java - Veri erisimi
CustomerService.java - Is mantigi
CustomerController.java - REST API
application.properties - DB config

API Endpoints:
GET/POST/PUT/DELETE /api/customers
POST /api/optimize-all

Hafta 3: Test ve Dogrulama

Postman ile API testleri yapildi
Veritabanina ornek veriler eklendi
Algoritma performansi test edildi
Rota optimizasyonu dogrulandi

Teknik Detaylar
Algoritma: Nearest Neighbor (O(n²))
Mesafe Hesaplama: Haversine formulu( OSMR olacak ) 
Veritabani: PostgreSQL customers tablosu
Test Sonuclari:

CRUD operasyonlari calisiyor
Rota optimizasyonu calisiyor
Veritabani entegrasyonu tamamlandi