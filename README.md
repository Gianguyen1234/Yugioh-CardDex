# YGO Card Dex - Version 1

## Overview
YGO Card Dex is an Android application built with Jetpack Compose that fetches and displays information about Yu-Gi-Oh! cards from the [YGOPRODeck API](https://db.ygoprodeck.com/api/v7/cardinfo.php). The app allows users to view a list of cards, search for specific cards, and see detailed information about each card. The data displayed includes card attributes like name, type, description, race, archetype, images, and prices. Users can interact with the cards through a top app bar, search functionality, and a detailed card view screen.

## Features
- **API Integration**: The app fetches data from the [YGOPRODeck API](https://db.ygoprodeck.com/api/v7/cardinfo.php) to display information about Yu-Gi-Oh! cards.
- **Card List Screen**: Displays a list of all fetched cards, including their name, type, and a small preview.
- **Card Detail Screen**: Clicking on a card from the list opens a detailed screen with full information, including:
  - **Card Name**
  - **Card Type**
  - **Card Description**
  - **Card Race**
  - **Archetype** (if available)
  - **Card Images**
  - **Card Prices**
- **Search Functionality**: Allows users to search for cards by name or type.
- **Top App Bar**: The application features a top app bar for navigation and search actions.
- **Card Type Decoration**: Each card type is decorated with a visual representation to make it more easily identifiable.
- **Card Archetype Decoration**: Cards belonging to specific archetypes are visually distinguished.

## API Integration

The application fetches data from the following API endpoint:
- **API Endpoint**: [https://db.ygoprodeck.com/api/v7/cardinfo.php](https://db.ygoprodeck.com/api/v7/cardinfo.php)

This API returns detailed information about Yu-Gi-Oh! cards, which is displayed in the app. The response contains a list of cards with various details, including their names, descriptions, types, races, archetypes, images, and prices.

### Example API Response

```json
{
  "data": [
    {
      "id": 34541863,
      "name": "\"A\" Cell Breeding Device",
      "type": "Spell Card",
      "humanReadableCardType": "Continuous Spell",
      "frameType": "spell",
      "desc": "During each of your Standby Phases, put 1 A-Counter on 1 face-up monster your opponent controls.",
      "race": "Continuous",
      "archetype": "Alien",
      "ygoprodeck_url": "https://ygoprodeck.com/card/a-cell-breeding-device-9766",
      "card_sets": [
        {
          "set_name": "Force of the Breaker",
          "set_code": "FOTB-EN043",
          "set_rarity": "Common",
          "set_rarity_code": "(C)",
          "set_price": "0"
        }
      ],
      "card_images": [
        {
          "id": 34541863,
          "image_url": "https://images.ygoprodeck.com/images/cards/34541863.jpg",
          "image_url_small": "https://images.ygoprodeck.com/images/cards_small/34541863.jpg",
          "image_url_cropped": "https://images.ygoprodeck.com/images/cards_cropped/34541863.jpg"
        }
      ],
      "card_prices": [
        {
          "cardmarket_price": "0.14",
          "tcgplayer_price": "0.19",
          "ebay_price": "0.99",
          "amazon_price": "24.45",
          "coolstuffinc_price": "0.25"
        }
      ]
    }
  ]
}
```

## Screens

### Card List Screen
- Displays all cards in a scrollable list with the card's name, type, and a small preview of the card's image.
- Tapping a card opens the Card Detail screen.

### Card Detail Screen
- Shows full information about the selected card.
- Displays card name, type, description, race, archetype (if available), images, and prices.
- Allows users to return to the Card List screen.

## UI/UX with Jetpack Compose
- **Top App Bar**: Provides a search bar and a title for easy navigation.
- **Search**: Users can search for specific cards by name or type. The results are dynamically filtered as the user types.
- **Decorations**: The card type and archetype are visually highlighted using custom UI elements like icons or colored labels.

## Technologies Used
- **Kotlin**: The app is developed in Kotlin.
- **Jetpack Compose**: UI is built using Jetpack Compose, a modern toolkit for building native UIs.
- **API Integration**: Cards are fetched from the [YGOPRODeck API](https://db.ygoprodeck.com/api/v7/cardinfo.php).
- **Android SDK**: The app is built for Android, using the latest Android development tools.

## Getting Started

### Prerequisites
Before running the application, ensure that you have the following installed:
- Android Studio with Jetpack Compose support
- Android Emulator or a physical Android device for testing

### Installation
1. Clone the repository:
   ```
   git clone https://github.com/Gianguyen1234/YGO-Card-Dex.git
   ```
2. Open the project in Android Studio.
3. Build and run the project on an emulator or physical device.

### Configuration
The app is configured to fetch card data from the [YGOPRODeck API](https://db.ygoprodeck.com/api/v7/cardinfo.php). Make sure to review the API integration and any authentication required (if applicable).

## Future Enhancements
- **Filter Cards**: Implement filtering options to sort cards by type, price, or archetype.
- **User Authentication**: Allow users to save their favorite cards or track owned cards.
- **Offline Support**: Cache card data for offline viewing.

## Contributing
Feel free to fork the repository and contribute to the project by submitting issues or pull requests.

## License
This project is open-source and available under the [MIT License](LICENSE).

