const config = {
  gatsby: {
    pathPrefix: '/',
    siteUrl: 'https://ibm-cloud-architecture.github.io/cloudpak-for-integration',
    gaTrackingId: null,
    trailingSlash: false,
  },
  header: {
    logo: 'static/cp4i-logo.svg',
    title:
      "<a href='https://ibm-cloud-architecture.github.io/cloudpak-for-integration'><img class='img-responsive' src='static/cp4i-logo.svg' alt='CP4I logo' /></a>",
    githubUrl: 'https://github.com/ibm-cloud-architecture/cloudpak-for-integration',
    helpUrl: '',
    tweetText: '',
    links: [{ text: '', link: '' }],
    search: {
      enabled: false,
      indexName: '',
      algoliaAppId: process.env.GATSBY_ALGOLIA_APP_ID,
      algoliaSearchKey: process.env.GATSBY_ALGOLIA_SEARCH_KEY,
      algoliaAdminKey: process.env.ALGOLIA_ADMIN_KEY,
    },
  },
  sidebar: {
    forcedNavOrder: [
      '/introduction', // add trailing slash if enabled above
      '/codeblock',
    ],
    collapsedNav: [
      '/codeblock', // add trailing slash if enabled above
    ],
    links: [
      {
        text: 'Cloud Pak for Integration',
        link: 'https://www.ibm.com/cloud/cloud-pak-for-integration',
      },
    ],
    frontline: false,
    ignoreIndex: true,
    title:
      "<a href='https://www.ibm.com/support/knowledgecenter/en/SSGT7J'>Knowledge Center</a><div class='greenCircle'></div><a href='https://www.ibm.com/demos/collection/Cloud-Pak-for-Integration/'>Demos</a>",
  },
  siteMetadata: {
    title: 'Cloud Pak for Integration | IBM',
    description: 'Documentation for IBM Cloud Pak for Integration',
    ogImage: null,
    docsLocation: 'https://github.com/ibm-cloud-architecture/cloudpak-for-integration',
    favicon: 'static/cp4i-logo.svg',
  },
  pwa: {
    enabled: false, // disabling this will also remove the existing service worker.
    manifest: {
      name: 'Gatsby Gitbook Starter',
      short_name: 'GitbookStarter',
      start_url: '/',
      background_color: '#6b37bf',
      theme_color: '#6b37bf',
      display: 'standalone',
      crossOrigin: 'use-credentials',
      icons: [
        {
          src: 'src/pwa-512.png',
          sizes: `512x512`,
          type: `image/png`,
        },
      ],
    },
  },
};

module.exports = config;
