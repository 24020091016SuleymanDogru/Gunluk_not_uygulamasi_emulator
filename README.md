<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/37b3cf64-cc05-4735-9a50-88e3a6916239" />
<img width="430" height="881" alt="image" src="https://github.com/user-attachments/assets/d4c68589-fda4-4ddb-8637-6fdf9f7a2d09" />
<img width="418" height="874" alt="image" src="https://github.com/user-attachments/assets/05fef1c3-ba64-42e4-be22-a6205c16e9a1" />

# 📒 Günlük Not Uygulaması

Android platformu için geliştirilmiş modern ve kullanıcı dostu bir not alma uygulaması.

## Özellikler

- 📝 Not ekleme, düzenleme ve silme
- 🎨 Her not için renk seçimi (mor, turkuaz, pembe, amber, yeşil)
- 🗃️ SQLite veritabanı ile kalıcı depolama
- 🔍 Notları listeleme ve arama
- 📱 Modern Material Design arayüzü

## Ekran Görüntüleri

| Ana Ekran | Not Ekleme/Düzenleme |
|-----------|----------------------|
| Notların listelendiği ana ekran | Başlık, içerik ve renk seçimi |

## Kullanılan Teknolojiler

- **Kotlin** — Uygulama dili
- **SQLite** — Yerel veritabanı
- **Material Design Components** — UI bileşenleri
- **ConstraintLayout / LinearLayout** — Ekran düzeni
- **AndroidX** — Geriye dönük uyumluluk

## Kurulum

1. Bu repoyu klonla:
   ```bash
   git clone https://github.com/24020091016SuleymanDogru/Gunluk_not_uygulamasi_emulator.git
   ```
2. Android Studio'da aç: **File → Open → proje klasörü**
3. Bir emülatör veya fiziksel cihaz bağla
4. **Run** butonuna bas

## Proje Yapısı

```
app/src/main/
├── java/com/example/gunun_sozu/
│   ├── MainActivity.kt          # Ana ekran, not listesi
│   ├── NoteDetailActivity.kt    # Not ekleme/düzenleme ekranı
│   ├── DatabaseHelper.kt        # SQLite veritabanı işlemleri
│   ├── NoteAdapter.kt           # ListView özel adaptörü
│   └── NoteItem.kt              # Not veri modeli
└── res/
    ├── layout/
    │   ├── activity_main.xml        # Ana ekran düzeni
    │   ├── activity_note_detail.xml # Detay ekranı düzeni
    │   └── item_note.xml            # Liste öğesi düzeni
    ├── drawable/                    # Şekil ve renk kaynakları
    └── values/
        ├── colors.xml
        ├── strings.xml
        └── themes.xml
```

## Veritabanı Şeması

```sql
CREATE TABLE notes (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    title   TEXT,
    content TEXT,
    color   TEXT
);
```

## Geliştirici

**Süleyman Doğru**  
Mobil Uygulama Geliştirme Dersi Projesi
