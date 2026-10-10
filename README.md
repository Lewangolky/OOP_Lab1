# Лицар: амуніція

Лабораторна робота №1 з ООП. Консольний застосунок, який:

1. описує ієрархію амуніції лицаря;
2. екіпірує лицаря амуніцією з файлу;
3. рахує загальну вартість амуніції;
4. сортує амуніцію за вагою;
5. шукає амуніцію в заданому діапазоні цін.

## Ієрархія амуніції

```
Ammunition (abstract)            name, weight, price; getSlot(), getDetails()
├── Armor (abstract)             material, protection
│   ├── Helmet                   visor            → HEAD
│   ├── Cuirass                                   → BODY
│   ├── Gauntlets                                 → HANDS
│   ├── Greaves                                   → LEGS
│   └── Shield                   blockChance      → OFF_HAND
└── Weapon (abstract)            damage           → MAIN_HAND (за замовчуванням)
    ├── Sword                    bladeLength
    ├── Mace                     spiked
    └── Dagger                                    → BELT (перевизначає слот)
```

- **Успадкування** — спільні властивості винесено в `Ammunition`, `Armor`, `Weapon`.
- **Поліморфізм** — кожен клас сам визначає свій слот (`getSlot()`) і опис
  (`getDetails()`); `Ammunition.toString()` — шаблонний метод, що їх використовує.
- **Інкапсуляція** — усі поля `private final`, значення перевіряються в
  конструкторах; `Knight` зберігає по одному предмету на слот і не дозволяє
  перевищити допустиму вагу (`OverloadException`).

## Пакети

| Пакет | Призначення |
|-------|-------------|
| `ua.oop.knight` | точка входу `Main` |
| `ua.oop.knight.model` | `Knight`, `EquipmentSlot` |
| `ua.oop.knight.model.ammunition` | базовий клас `Ammunition` |
| `ua.oop.knight.model.ammunition.armor` | обладунки та `Material` |
| `ua.oop.knight.model.ammunition.weapon` | зброя |
| `ua.oop.knight.service` | `AmmunitionService` (вартість, сортування, пошук), `EquipmentService` (екіпірування) |
| `ua.oop.knight.repository` | читання амуніції з файлу |
| `ua.oop.knight.config` | параметри ініціалізації |
| `ua.oop.knight.ui` | мінімальне консольне меню |
| `ua.oop.knight.exception` | винятки застосунку |

## Файли ініціалізації

`config/knight.properties` — ім'я лицаря, максимальна вага та шлях до файлу
амуніції (відносно каталогу `config`).

`config/ammunition.csv` — амуніція, по одному предмету в рядку, поля розділені
`;`. Рядки, що починаються з `#`, ігноруються. Формат кожного типу описано
в коментарі на початку файлу.

## Збірка та запуск

Потрібні JDK 17+ та Maven 3.9+.

```bash
mvn clean verify
```

Команда компілює код, запускає тести (JUnit 5 + Mockito) і перевіряє покриття
JaCoCo (100% рядків і гілок, звіт — `target/site/jacoco/index.html`).

```bash
java -jar target/knight-ammunition-1.0.0.jar
```

Запускати з кореня проєкту. Інший файл конфігурації можна передати аргументом:
`java -jar target/knight-ammunition-1.0.0.jar path/to/knight.properties`.
