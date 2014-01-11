A story is a collection of scenarios

Narrative:
In order to get all modifications of item name
As a yandex advertising creater
I want to generate them

Scenario:  Name modifications

Given keywords:
| type        | keyword                 |
| NAME        | DISCOVERY               |
| AREA        | тренажеры               |
Given template words:
| type       | template word           |
| ACTION     | купить                  |
| LOCATION   | нежин                   |

When generate keyphrases
Then keyphrases returned are:
| keyphrases                |
|тренажеры купить           |
|тренажеры купить нежин     |
|тренажеры нежин            |
|DISCOVERY купить           |
|DISCOVERY купить нежин     |
|DISCOVERY нежин            |
