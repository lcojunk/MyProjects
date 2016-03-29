(function () {
    angular.module('searchApp.core')
        .constant('STATES', {
            ROOT: {
                NAME: 'root',
                SEARCH_VIEW: {
                    NAME: 'root.search_view',
                    COMPONENTS: {
                        NAME: 'root.search_view.components',
                        URL: '/search'
                    }
                }
            }
        })
        .constant('TEMPLATES', {
            SEARCH_VIEW: 'search/search.html',
            SEARCH_INPUT: 'search/input/input.html',
            SEARCH_RESULT: 'search/result/result.html',
            DETAIL: 'search/detail/detail.html',
            MAIN: 'core/main/main.html',
            MULTI_CHOICE: 'widgets/multi-choice/multi-choice.tpl.html',
            SORT_INDICATOR: 'widgets/sort-indicator/sort-indicator.tpl.html',
            RANGE_INPUT: 'widgets/range-input/range-input.tpl.html',
            PAGINATION: 'core/templates/pagination.tpl.html'
        })
        .constant('UTIL', {
            SLIDER_RENDER_FORCE: 'rzSliderForceRender',
            CONTROLLER_AS_VM: 'vm',
            TOKEN_COOKIE_NAME: 'Token'
        })
        .constant('STRINGS', {
            NO_RESULTS: 'Die Suche ergab keine Ergebnisse. Passen Sie die Suchkriterien an.',
            RESULT_POPOVER: {
                TITLE: 'Suchergebnisse',
                CONTENT: 'In diesem Bereich werden alle Ergebnisse einer Referenzsuche angezeigt. Sind Filter aktiv,' +
                ' so werden nur jene Referenzen angezeigt, welche den Filtereinstellungen entsprechen.'
            },
            ERROR: {
                NO_RESULTS: 'Keine Ergebnisse'
            }
        })
        .constant('VALUES', {
            PERIOD: {
                NO_LIMITATIONS: {NAME: 'Keine Einschränkung', VALUE: -1},
                PROJECT_NOT_FINISHED: {NAME: 'Projekt dauert noch an', VALUE: 0},
                PROJECT_END_ONE_YEAR_AGO: {NAME: 'Projektende vor bis zu 1 Jahr', VALUE: 1},
                PROJECT_END_THREE_YEARS_AGO: {NAME: 'Projektende vor bis zu 3 Jahr', VALUE: 3},
                PROJECT_END_FIVE_YEARS_AGO: {NAME: 'Projektende vor bis zu 5 Jahr', VALUE: 5}
            },
            CATEGORY: {
                CLIENT: 'Kundenname',
                PROJECT: 'Projektname',
                LOB: 'Line of Business',
                BRANCH: 'Branche',
                TECHNOLOGY: 'Technologien',
                SERVICE: {
                    NAME: 'Wartungsvertrag',
                    EXISTS: 'vorhanden',
                    DOES_NOT_EXISTS: 'nicht vorhanden'
                },
                SERVICE_END: {
                    NAME: 'Wartungsende',
                    ONE_YEAR_AGO: {name: 'nicht länger als 1 Jahr her', length: 1},
                    THREE_YEARS_AGO: {name: 'nicht länger als 3 Jahre her', length: 3},
                    FIVE_YEARS_AGO: {name: 'nicht länger als 5 Jahre her', length: 5}
                }
            },
            USER_GROUP: {
                ADMIN: 'ADMIN',
                EDITORIAL: 'EDITOR',
                BASIC: 'BASIC'
            },
            STATUS: {
                FULLY_RELEASED: {
                    IDENTIFIER: 'FULLY_RELEASED',
                    TEXT: 'Freigegeben'
                },
                NOT_RELEASED: {
                    IDENTIFIER: 'NOT_RELEASED',
                    TEXT: 'Keine Freigabe'
                },
                ANONYMOUSLY_RELEASED: {
                    IDENTIFIER: 'ANONYMOUSLY_RELEASED',
                    TEXT: 'Anonym freigegeben'
                },
                INDIVIDUALLY_RELEASED: {
                    IDENTIFIER: 'INDIVIDUALLY_RELEASED',
                    TEXT: 'Einzelfreigabe'
                }
            },
            SORT_TYPES: {
                relevance: {label: 'Relevanz', value: 'RELEVANCE'},
                projectName: {label: 'Projektname', value: 'PROJECTNAME'},
                clientName: {label: 'Kundenname', value: 'CLIENTNAME'},
                manDays: {label: 'Personentage', value: 'PT'},
                volume: {label: 'Volumen', value: 'VOLUMES'},
                lob: {label: 'Line of Business', value: 'LOB'},
                industry: {label: 'Branche', value: 'BRANCH'},
                technology: {label: 'Technologie', value: 'TECHNOLOGY'},
                projectStart:{label: 'Projektbeginn', value: 'PROJECTSTART'},
                projectEnd: {label: 'Projektende', value: 'PROJECTEND'},
                state: {label: 'Status', value: 'STATUS'}
            },
            DEFAULT_PAGE_SIZE: 30
        });
})();