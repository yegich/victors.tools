A story is a collection of scenarios

Narrative:
In order to get all modifications of item name
As a yandex advertising creater
I want to generate them

Scenario:  Name modifications

Given keywords:
| type | keyword                 |
| NAME | USA STYLE SS-82001      |
| NAME | DISCOVERY F-16          |
| NAME | USA STYLE WNQ SS-8618B  |
When generate keyword modification
Then modifications returned are:
| modifications           |
| USA STYLESS-82001       |
| USASTYLE SS-82001       |
| USA STYLE SS82001       |
| DISCOVERYF-16           |
| DISCOVERY F16           |
| USASTYLE WNQ SS-8618B   |
| USA STYLEWNQ SS-8618B   |
| USA STYLE WNQSS-8618B   |
| USA STYLE WNQ SS8618B   |
