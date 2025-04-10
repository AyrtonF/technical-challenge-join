import "./card.css"

interface CardProps{
    name: string,
    description: string,
    image: string,



}


export function Card({name, description, image}: CardProps){
    return(
        <div className="card">
            <img className="card-img-top" src={image} alt="" />
           <div className="card-body"></div>
            <h2 className="card-title">{name}</h2>
            <p className="card-text"><b>Descrição:</b>{description}</p>
            <a href="#" className="btn">Ler mais</a>
        </div>
    )

}