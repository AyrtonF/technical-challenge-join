
import { Routes, Route } from 'react-router-dom'
import Home from './pages/Home/Home'
import ProductList from './pages/ProductList/ProductList'
import AddProduct from './pages/AddProduct/AddProduct'
import EditProduct from './pages/EditProduct/EditProduct'
import ViewProduct from "./pages/ViewProduct/ViewProduct"
import Navigation from './components/Navigation/Navigation'

import './App.css'


function App() {
 

  return (
    <div className="app-container">
    <Navigation />
    <div className="container mt-4">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/products" element={<ProductList />} />
        <Route path="/products/add" element={<AddProduct />} />
        <Route path="/products/edit/:id" element={<EditProduct />} />
        <Route path="/products/view/:id" element={<ViewProduct />} />
      </Routes>
    </div>
  </div>
  )
}

export default App
