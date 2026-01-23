import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Provider } from 'react-redux'
import { store } from './store/store.js'
import React from 'react'
// import './index.css'
import App from './App.jsx'
import { AuthProvider } from 'react-oauth2-code-pkce'
import { authConfig } from './authConfig.js'

const root = createRoot(document.getElementById('root'))
root.render(
  <AuthProvider authConfig={authConfig}>
    <Provider store={store}>
      <App />
    </Provider>
  </AuthProvider>
)
