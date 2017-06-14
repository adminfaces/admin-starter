var log = console.log.bind(console);
var err = console.error.bind(console);

var version = '1';
var cacheName = 'pwa-client-v' + version;
var dataCacheName = 'pwa-client-data-v' + version;
var appShellFilesToCache = [
    'index.xhtml',
    'resources/starter.css',
    '/admin-starter/javax.faces.resource/theme.css.xhtml?ln=primefaces-admin',
    '/admin-starter/javax.faces.resource/fa/font-awesome.css.xhtml?ln=primefaces&amp;v=6.1'
];

self.addEventListener('install', (e) => {
  e.waitUntil(self.skipWaiting());
  log('Service Worker: Installed');

  /*e.waitUntil(
    caches.open(cacheName).then((cache) => {
      log('Service Worker: Caching App Shell');
      return cache.addAll(appShellFilesToCache);
    })
  );*/
});

self.addEventListener('activate', (e) => {
  e.waitUntil(self.clients.claim());
  log('Service Worker: Active');

  /*e.waitUntil(
    caches.keys().then((keyList) => {
      return Promise.all(keyList.map((key) => {

        if (key !== cacheName) {
          log('Service Worker: Removing old cache', key);
          return caches.delete(key);
        }

      }));
    })
  );*/
});

self.addEventListener('fetch', (e) => {
  log('Service Worker: Fetch URL ', e.request.url);

  // Match requests for data and handle them separately
  /*e.respondWith(
    caches.match(e.request.clone()).then((response) => {
      return response || fetch(e.request.clone()).then((r2) => {
          return caches.open(dataCacheName).then((cache) => {
            console.log('Service Worker: Fetched & Cached URL ', e.request.url);
            cache.put(e.request.url, r2.clone());
            return r2.clone();
          });
        });
    })
  );*/
});

