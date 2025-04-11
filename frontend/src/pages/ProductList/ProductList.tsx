import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import ProductCard from '../../components/ProductCard/ProductCard'
import SearchBar from '../../components/Search/SearchBar'
import Pagination from '../../components/Pagination/Pagination'
import ProductService from '../../services/productService'
import { FaPlus } from 'react-icons/fa'
import { toast } from 'react-toastify'
import { Product, ProductPage } from '../../types/product'
import "./ProductList.css"

const ProductList = () => {
  const [products, setProducts] = useState<Product[]>([])
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const [searchTerm, setSearchTerm] = useState('')
  const [isLoading, setIsLoading] = useState(false)

  const fetchProducts = async (page: number = 0, search: string = '') => {
    setIsLoading(true)
    try {
      const data: ProductPage = await ProductService.getAll(page, 6, search)
      setProducts(data.content)
      setTotalPages(data.totalPages)
    } catch (error) {
      toast.error('Erro ao carregar os produtos')
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchProducts(currentPage, searchTerm)
  }, [currentPage, searchTerm])

  const handleSearch = (term: string) => {
    setSearchTerm(term)
    setCurrentPage(0)
  }

  const handlePageChange = (page: number) => {
    setCurrentPage(page)
  }

  const handleDelete = (id: string) => {
    setProducts(products.filter((product) => product.id !== id))
  }

  return (
    <div className="container">
      <div className="header">
        <h2 className="title">Lista de Produtos</h2>
        <Link to="/products/add" className="addButton">
          <FaPlus className="addIcon" /> Adicionar Produto
        </Link>
      </div>

      <SearchBar onSearch={handleSearch} />

      {isLoading ? (
        <div className="loadingContainer">
          <div className="spinner"></div>
          <span className="loadingText">Carregando...</span>
        </div>
      ) : products.length === 0 ? (
        <div className="emptyMessage">
          Nenhum produto encontrado. Adicione um novo produto para começar.
        </div>
      ) : (
        <>
          <div className="productGrid">
            {products.map((product) => (
              <ProductCard
                key={product.id}
                product={product}
                onDelete={handleDelete}
              />
            ))}
          </div>
          {totalPages > 1 && (
            <Pagination
              currentPage={currentPage}
              totalPages={totalPages}
              onPageChange={handlePageChange}
            />
          )}
        </>
      )}
    </div>
  )
}

export default ProductList
