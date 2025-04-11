import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { FaArrowLeft, FaEdit } from 'react-icons/fa'
import ProductService from '../../services/productService'
import { toast } from 'react-toastify'
import { Product } from '../../types/product'
import "./ViewProduct.css"

const ViewProduct = () => {
  const { id } = useParams<{ id: string }>()
  const [product, setProduct] = useState<Product | null>(null)
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        if (!id) {
          throw new Error('ID do produto não fornecido')
        }
        const data = await ProductService.getById(id)
        setProduct(data)
      } catch (error) {
        toast.error('Erro ao carregar o produto')
      } finally {
        setIsLoading(false)
      }
    }
    fetchProduct()
  }, [id])

  if (isLoading) {
    return <div className="loading">Carregando...</div>
  }

  if (!product) {
    return <div className="error">Produto não encontrado</div>
  }

  return (
    <div className="card">
      <div className="cardBody">
        <div className="header">
          <Link to="/products" className="backButton">
            <FaArrowLeft className="backIcon" /> Voltar para Produtos
          </Link>
          <Link
            to={`/products/edit/${product.id}`}
            className="editButton"
          >
            <FaEdit className="editIcon" /> Editar
          </Link>
        </div>

        <div className="content">
          <div className="imageContainer">
            {product.imageUrl ? (
              <img
                src={product.imageUrl}
                alt={product.name}
                className="image"
              />
            ) : (
              <div className="noImage">Imagem não disponível</div>
            )}
          </div>
          <div className="details">
            <h2 className="productName">{product.name}</h2>
            <p className="description">{product.description}</p>
            <div className="detailItem">
              <h5 className="detailLabel">Preço:</h5>
              <span className="price">
                R$ {product.price.toFixed(2)}
              </span>
            </div>
            <div className="detailItem">
              <h5 className="detailLabel">Quantidade em Estoque:</h5>
              <span className="quantity">{product.quantity}</span>
            </div>
            {product.id && (
              <div className="detailItem">
                <h5 className="detailLabel">ID do Produto:</h5>
                <span className="id">{product.id}</span>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

export default ViewProduct
