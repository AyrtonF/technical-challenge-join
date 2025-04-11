import { useState, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { toast } from 'react-toastify'
import ProductService from '../../services/productService'
import { Product } from '../../types/product'
import "./ProductForm.css"

const ProductForm = () => {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()

  const [product, setProduct] = useState<Omit<Product, 'id'> | Product>({
    name: '',
    description: '',
    price: 0,
    quantity: 0,
    imageUrl: '',
  })

  const [errors, setErrors] = useState<{ [key: string]: string }>({})
  const [isLoading, setIsLoading] = useState(false)

  useEffect(() => {
    if (id) {
      const fetchProduct = async () => {
        setIsLoading(true)
        try {
          const data = await ProductService.getById(id)
          setProduct(data)
        } catch (error) {
          toast.error('Erro ao carregar o produto')
          navigate('/products')
        } finally {
          setIsLoading(false)
        }
      }
      fetchProduct()
    }
  }, [id, navigate])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target
    setProduct((prev) => ({ ...prev, [name]: value }))
  }

  const validate = () => {
    const newErrors: { [key: string]: string } = {}

    if (!product.name || product.name.trim().length < 3) {
      newErrors.name = 'O nome do produto deve ter pelo menos 3 caracteres.'
    }

    if (product.price <= 0) {
      newErrors.price = 'O preço deve ser maior que zero.'
    }

    if (!Number.isInteger(Number(product.quantity)) || product.quantity < 0) {
      newErrors.quantity = 'A quantidade deve ser um número inteiro não negativo.'
    }

    if (product.imageUrl && !/^https?:\/\/.+\..+/.test(product.imageUrl)) {
      newErrors.imageUrl = 'URL da imagem inválida.'
    }

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    if (!validate()) return

    setIsLoading(true)

    try {
      if (id) {
        await ProductService.update(id, product as Product)
        toast.success('Produto atualizado com sucesso!')
      } else {
        await ProductService.create(product)
        toast.success('Produto criado com sucesso!')
      }
      navigate('/products')
    } catch (error) {
      toast.error('Erro ao salvar o produto')
    } finally {
      setIsLoading(false)
    }
  }

  if (isLoading && id) {
    return <div className="loading">Carregando...</div>
  }

  return (
    <div className="formCard">
      <div className="cardBody">
        <h2 className="cardTitle">
          {id ? 'Editar Produto' : 'Adicionar Novo Produto'}
        </h2>
        <form onSubmit={handleSubmit} className="form">
          <div className="formGroup">
            <label htmlFor="name" className="label">Nome do Produto</label>
            <input
              type="text"
              className="input"
              id="name"
              name="name"
              value={product.name}
              onChange={handleChange}
              required
            />
            {errors.name && <small className="error">{errors.name}</small>}
          </div>

          <div className="formGroup">
            <label htmlFor="description" className="label">Descrição</label>
            <textarea
              className="textarea"
              id="description"
              name="description"
              rows={3}
              value={product.description}
              onChange={handleChange}
            ></textarea>
          </div>

          <div className="row">
            <div className="col">
              <label htmlFor="price" className="label">Preço (R$)</label>
              <input
                type="number"
                step="0.01"
                min="0"
                className="input"
                id="price"
                name="price"
                value={product.price}
                onChange={handleChange}
                required
              />
              {errors.price && <small className="error">{errors.price}</small>}
            </div>

            <div className="col">
              <label htmlFor="quantity" className="label">Quantidade</label>
              <input
                type="number"
                min="0"
                className="input"
                id="quantity"
                name="quantity"
                value={product.quantity}
                onChange={handleChange}
                required
              />
              {errors.quantity && <small className="error">{errors.quantity}</small>}
            </div>
          </div>

          <div className="formGroup">
            <label htmlFor="imageUrl" className="label">URL da Imagem</label>
            <input
              type="url"
              className="input"
              id="imageUrl"
              name="imageUrl"
              value={product.imageUrl || ''}
              onChange={handleChange}
              placeholder="https://exemplo.com/imagem.jpg"
            />
            {errors.imageUrl && <small className="error">{errors.imageUrl}</small>}
          </div>

          <div className="formActions">
            <button
              type="button"
              className="cancelButton"
              onClick={() => navigate('/products')}
            >
              Cancelar
            </button>
            <button 
              type="submit" 
              className="submitButton" 
              disabled={isLoading}
            >
              {isLoading ? 'Salvando...' : id ? 'Atualizar' : 'Salvar'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default ProductForm
